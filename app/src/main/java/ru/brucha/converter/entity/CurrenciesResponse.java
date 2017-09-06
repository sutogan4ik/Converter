package ru.brucha.converter.entity;

/**
 * Created by prog on 05.09.2017.
 */

public class CurrenciesResponse {
    private ValCurs currencies;
    private ErrorType errorType;

    public ValCurs getCurrencies() {
        return currencies;
    }

    public void setCurrencies(ValCurs valCurs) {
        this.currencies = valCurs;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}
