package ru.brucha.converter.presenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;

import ru.brucha.converter.FileUtil;
import ru.brucha.converter.entity.ErrorType;
import ru.brucha.converter.entity.ValCurs;
import ru.brucha.converter.entity.Valute;
import ru.brucha.converter.interactor.CurrencyInteractor;
import ru.brucha.converter.view.CurrencyView;

/**
 * Created by prog on 06.09.2017.
 */

public class CurrencyPresenterImpl implements CurrencyPresenter{
    private CurrencyInteractor interactor;
    private CurrencyView view;
    private File cacheFile;
    private FileUtil fileUtil;

    public CurrencyPresenterImpl(CurrencyInteractor interactor, CurrencyView view, File cacheFile) {
        this.interactor = interactor;
        this.view = view;
        this.cacheFile = cacheFile;
        fileUtil = new FileUtil();
    }

    @Override
    public void destroy() {
        view = null;
        cacheFile = null;
        fileUtil = null;
        if(interactor != null){
            interactor.destroy();
        }
        interactor = null;
    }

    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public void initData() {
        if(view != null){
            view.showContent(false);
            view.showProgress(true);
        }
        interactor.getData("http://www.cbr.ru/scripts/XML_daily.asp", new CurrencyInteractor.CurrencyLoadListener() {
            @Override
            public void onLoadComplete(ValCurs currencies) {
                currencies.getList().add(0, getRub());
                fileUtil.saveToFile(currencies, cacheFile);
                showUI(currencies);
            }

            @Override
            public void onLoadError(ErrorType errorType) {
                ValCurs curs = fileUtil.loadFile(cacheFile);
                if(curs != null){
                    showUI(curs);
                }else{
                    switch (errorType){
                        case CONNECTION_ERROR:
                            showError("Connection error");
                            break;
                        case PARSE_ERROR:
                            showError("Data not valid");
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void calculate(Valute source, Valute converted, String count) {
        if(count.isEmpty() || count.equals("0")){
            if(view != null){
                view.showInputError("Введи что-то");
            }
            return;
        }
        Double sourceValue = Double.parseDouble(source.getValue().replaceAll(",", "."));
        Double convertedValue = Double.parseDouble(converted.getValue().replaceAll(",", "."));
        Double countDouble = Double.parseDouble(count);
        float toRub = (float) (sourceValue * countDouble / source.getNominal());
        float toConverted = (float) (toRub / convertedValue * converted.getNominal());
        String result = String.format("%.2f", toConverted) + " " + converted.getCharCode();
        if(view != null){
            view.showResult(result);
        }
    }

    private Valute getRub(){
        Valute valute = new Valute();
        valute.setName("Российский рубль");
        valute.setCharCode("RUB");
        valute.setValue("1");
        valute.setNominal(1);
        return valute;
    }

    private void showUI(ValCurs currencies){
        if(view != null){
            view.showUI(currencies);
            view.showContent(true);
            view.showProgress(false);
        }
    }

    private void showError(String message){
        if (view != null){
            view.showError(message);
        }
    }

}
