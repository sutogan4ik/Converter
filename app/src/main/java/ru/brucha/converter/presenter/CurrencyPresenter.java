package ru.brucha.converter.presenter;

import ru.brucha.converter.entity.Valute;

/**
 * Created by prog on 06.09.2017.
 */

public interface CurrencyPresenter {
    void initData();

    void calculate(Valute source, Valute converted, String count);

    void destroy();
}
