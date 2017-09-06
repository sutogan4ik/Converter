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

import ru.brucha.converter.entity.ErrorType;
import ru.brucha.converter.entity.ValCurs;
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
    @Captor
    ArgumentCaptor<CurrencyInteractor.CurrencyLoadListener> captor;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new CurrencyPresenterImpl(interactor, view, file);
    }

    @Test
    public void initDataTest() throws Exception {
        when(file.exists()).thenReturn(false);
        when(file.getParentFile()).thenReturn(file);
        presenter.initData();
        verify(interactor).getData(anyString(), captor.capture());
        captor.getValue().onLoadComplete(new ValCurs());
        verify(view).showUI(any(ValCurs.class));

    }

    @Test
    public void initDataWithError() throws Exception {
        presenter.initData();
        verify(interactor).getData(anyString(), captor.capture());
        captor.getValue().onLoadError(ErrorType.CONNECTION_ERROR);
        verify(view).showError(anyString());
    }
}