package ru.brucha.converter.interactor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;

import ru.brucha.converter.entity.CurrenciesResponse;
import ru.brucha.converter.entity.ErrorType;
import ru.brucha.converter.entity.ValCurs;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by prog on 05.09.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CurrencyInteractorImplTest {

    CurrencyInteractorImpl interactor;

    @Mock
    CurrencyInteractor.CurrencyLoadListener listener;

    @Mock
    CurrencyInteractorImpl.Loader loader;

    CurrenciesResponse response;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        interactor = new CurrencyInteractorImpl();
    }

    @Test
    public void parseTest() throws Exception {
        URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(5000);
        Serializer serializer = new Persister();
        ValCurs valCurs = serializer.read(ValCurs.class, connection.getInputStream());
        assertNotNull(valCurs);
        assertEquals(valCurs.getList().size(), 34);
    }

    @Test
    public void getData() throws Exception {
        interactor.getData("", listener);
        response = new CurrenciesResponse();
        response.setCurrencies(new ValCurs());
        response.setErrorType(ErrorType.NONE);
        interactor.getLoader().onPostExecute(response);
        verify(listener).onLoadComplete(any(ValCurs.class));
    }

    @Test
    public void getDataWitError() throws Exception {
        interactor.getData("", listener);
        response = new CurrenciesResponse();
        response.setErrorType(ErrorType.PARSE_ERROR);
        interactor.getLoader().onPostExecute(response);
        verify(listener).onLoadError(ErrorType.PARSE_ERROR);

    }
}