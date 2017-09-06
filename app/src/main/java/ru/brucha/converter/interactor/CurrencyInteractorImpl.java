package ru.brucha.converter.interactor;

import android.os.AsyncTask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import ru.brucha.converter.entity.ErrorType;
import ru.brucha.converter.entity.ValCurs;
import ru.brucha.converter.entity.CurrenciesResponse;

/**
 * Created by prog on 05.09.2017.
 */

public class CurrencyInteractorImpl implements CurrencyInteractor {
    private Loader loader;

    public Loader getLoader() {
        return loader;
    }

    @Override
    public void getData(String url, final CurrencyLoadListener listener) {
        loader = new Loader(){
            @Override
            public void onPostExecute(CurrenciesResponse currenciesResponse) {
                super.onPostExecute(currenciesResponse);
                switch (currenciesResponse.getErrorType()){
                    case NONE:
                        listener.onLoadComplete(currenciesResponse.getCurrencies());
                        break;
                    default:
                        listener.onLoadError(currenciesResponse.getErrorType());
                }
            }
        };
        loader.execute(url);
    }

    @Override
    public void destroy() {
        if(loader != null) {
            loader.cancel(true);
        }
        loader = null;
    }

    public static abstract class Loader extends AsyncTask<String, String, CurrenciesResponse> {

        @Override
        protected CurrenciesResponse doInBackground(String... params) {
            CurrenciesResponse response = new CurrenciesResponse();
            try {
                URL url = new URL(params[0]);
                URLConnection connection = url.openConnection();
                connection.setConnectTimeout(5000);
                Serializer serializer = new Persister();
                ValCurs valCurs = serializer.read(ValCurs.class, connection.getInputStream());
                if (valCurs != null) {
                    response.setCurrencies(valCurs);
                    response.setErrorType(ErrorType.NONE);
                    return response;
                }
                response.setErrorType(ErrorType.PARSE_ERROR);
            } catch (MalformedURLException e) {
                response.setErrorType(ErrorType.CONNECTION_ERROR);
                e.printStackTrace();
            } catch (IOException e) {
                response.setErrorType(ErrorType.CONNECTION_ERROR);
                e.printStackTrace();
            } catch (Exception e) {
                response.setErrorType(ErrorType.PARSE_ERROR);
                e.printStackTrace();
            }
            return response;
        }

        @Override
        public void onPostExecute(CurrenciesResponse currenciesResponse) {
            super.onPostExecute(currenciesResponse);
        }
    }
}
