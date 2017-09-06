package ru.brucha.converter.interactor;

import ru.brucha.converter.entity.ErrorType;
import ru.brucha.converter.entity.ValCurs;

/**
 * Created by prog on 05.09.2017.
 */

public interface CurrencyInteractor {
    void getData(String url, CurrencyLoadListener listener);
    void destroy();
    interface CurrencyLoadListener{
        void onLoadComplete(ValCurs currencies);
        void onLoadError(ErrorType errorType);
    }
}
