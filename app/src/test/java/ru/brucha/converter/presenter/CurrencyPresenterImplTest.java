package ru.brucha.converter.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.List;

import ru.brucha.converter.FileUtil;
import ru.brucha.converter.entity.ErrorType;
import ru.brucha.converter.entity.ValCurs;
import ru.brucha.converter.entity.Valute;
import ru.brucha.converter.interactor.CurrencyInteractor;
import ru.brucha.converter.view.CurrencyView;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by prog on 06.09.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CurrencyPresenterImplTest {
    CurrencyPresenterImpl presenter;

    @Mock
    CurrencyInteractor interactor;
    @Mock
    CurrencyView view;
    @Mock
    File file;

    @Mock
    FileUtil util;

    @Mock
    List<Valute> valutes;

    @Mock
    ValCurs curs;

    @Captor
    ArgumentCaptor<CurrencyInteractor.CurrencyLoadListener> captor;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new CurrencyPresenterImpl(interactor, view, file);
        presenter.setFileUtil(util);
    }

    @Test
    public void initDataTest() throws Exception {
        when(file.exists()).thenReturn(false);
        when(file.getParentFile()).thenReturn(file);
        when(curs.getList()).thenReturn(valutes);
        presenter.initData();
        verify(interactor).getData(anyString(), captor.capture());
        captor.getValue().onLoadComplete(curs);
        verify(view).showUI(any(ValCurs.class));

    }

    @Test
    public void initDataWithError() throws Exception {
        when(util.loadFile(file)).thenReturn(curs);
        when(file.exists()).thenReturn(false);
        when(file.getParentFile()).thenReturn(file);
        presenter.initData();
        verify(interactor).getData(anyString(), captor.capture());
        captor.getValue().onLoadError(ErrorType.CONNECTION_ERROR);
        verify(view).showUI(curs);
    }

    @Test
    public void initDataWithErrorAndEmptyCache() throws Exception {
        when(util.loadFile(file)).thenReturn(null);
        when(file.exists()).thenReturn(false);
        when(file.getParentFile()).thenReturn(file);
        presenter.initData();
        verify(interactor).getData(anyString(), captor.capture());
        captor.getValue().onLoadError(ErrorType.CONNECTION_ERROR);
        verify(view).showError(anyString());
    }

    @Test
    public void calculateTest() throws Exception {
        Valute valute = new Valute();
        valute.setName("Российский рубль");
        valute.setCharCode("RUB");
        valute.setValue("1");
        valute.setNominal(1);
        presenter.calculate(valute, valute, "100");
        String result = 100.0f + " " + valute.getCharCode();
        verify(view).showResult(result);
    }

    @Test
    public void calculateWithInputError() throws Exception {
        Valute valute = new Valute();
        valute.setName("Российский рубль");
        valute.setCharCode("RUB");
        valute.setValue("1");
        valute.setNominal(1);
        presenter.calculate(valute, valute, "");
        verify(view).showInputError(anyString());

    }
}