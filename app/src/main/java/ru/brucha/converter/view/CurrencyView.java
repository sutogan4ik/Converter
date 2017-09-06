package ru.brucha.converter.view;

import ru.brucha.converter.entity.ErrorType;
import ru.brucha.converter.entity.ValCurs;

/**
 * Created by prog on 06.09.2017.
 */

public interface CurrencyView {
    void showUI(ValCurs currencies);

    void showError(String errorMessage);

    void showResult(String result);

    void showContent(boolean show);

    void showProgress(boolean show);

    void showInputError(String message);
}
