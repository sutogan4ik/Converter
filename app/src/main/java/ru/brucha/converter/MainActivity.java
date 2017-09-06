package ru.brucha.converter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import ru.brucha.converter.adapter.CurrencyAdapter;
import ru.brucha.converter.entity.ValCurs;
import ru.brucha.converter.entity.Valute;
import ru.brucha.converter.interactor.CurrencyInteractorImpl;
import ru.brucha.converter.presenter.CurrencyPresenter;
import ru.brucha.converter.presenter.CurrencyPresenterImpl;
import ru.brucha.converter.view.CurrencyView;

public class MainActivity extends AppCompatActivity implements CurrencyView{
    private CurrencyPresenter presenter;

    TextView lastDate;
    AppCompatSpinner source;
    AppCompatSpinner converted;
    Button calculate;
    View container;
    ProgressBar progressBar;
    TextInputEditText inputEditText;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        source = (AppCompatSpinner) findViewById(R.id.spinner_source);
        converted = (AppCompatSpinner) findViewById(R.id.spinner_converted);
        lastDate = (TextView) findViewById(R.id.date);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        container = findViewById(R.id.container);
        inputEditText = (TextInputEditText) findViewById(R.id.tiet_count);
        result = (TextView) findViewById(R.id.result);
        calculate = (Button) findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Valute s = (Valute) source.getSelectedItem();
                Valute c = (Valute) converted.getSelectedItem();
                presenter.calculate(s, c, inputEditText.getText().toString());
            }
        });
        presenter = new CurrencyPresenterImpl(new CurrencyInteractorImpl(), this, new File(getCacheDir(), "valutes/cache.data"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void showUI(ValCurs currencies) {
        String dateText = "Последнее обновление: " + currencies.getDate();
        lastDate.setText(dateText);
        CurrencyAdapter adapter = new CurrencyAdapter(currencies.getList());
        source.setAdapter(adapter);
        converted.setAdapter(adapter);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(findViewById(R.id.toolbar), errorMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.initData();
                    }
                }).show();
    }

    @Override
    public void showResult(String result) {
        Log.d("Andrey", "Result = " + result);
        this.result.setText(result);
    }

    @Override
    public void showContent(boolean show) {
        if(show){
            container.setVisibility(View.VISIBLE);
        }else{
            container.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showProgress(boolean show) {
        if(show){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showInputError(String message) {
        inputEditText.setError(message);
    }
}
