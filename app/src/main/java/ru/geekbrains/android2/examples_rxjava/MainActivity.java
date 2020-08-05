package ru.geekbrains.android2.examples_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


public class MainActivity extends AppCompatActivity {

    TextView textViewDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewDisplay = findViewById(R.id.textViewDisplay);

        initObservableJust();
        initObservableFromArray();
        initObservableEditTextEmitter();
        initObservableOperators();
    }

    // Пример 1. Стандарт
    private void initObservableJust() {
        Observable<String> observableJust = Observable.just("a", "b", "c", "d");

        Observer<String> observerJust = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                String str = textViewDisplay.getText().toString();
                str = str + s;
                textViewDisplay.setText(str);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Button btn = findViewById(R.id.btnObservableJust);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observableJust.subscribe(observerJust);
            }
        });
    }

    // Пример 2
    private void initObservableFromArray() {
        String [] myArray= {"Wi", "Su", "Au", "Sp" };
        Observable<String> observable = Observable.fromArray(myArray);

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                String str = textViewDisplay.getText().toString();
                str = str + s;
                textViewDisplay.setText(str);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        findViewById(R.id.btnObservableFromArray)
                .setOnClickListener(v -> observable.subscribe(observer));
    }

    // Пример 3
    private void initObservableEditTextEmitter() {
        EditText editText = findViewById(R.id.editText);
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!emitter.isDisposed()) { //если еще не отписались
                            emitter.onNext(editable.toString()); //отправляем текущее состояние
                        }
                    }
                };

                editText.addTextChangedListener(watcher);
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                String str = textViewDisplay.getText().toString();
                str = str + s;
                textViewDisplay.setText(str);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);
    }

    // Пример 4
    private void initObservableOperators() {

        Function<String, String> multiplyFunction = new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return Integer.toString(Integer.parseInt(s) * 2);
            }
        };
//
//        Function<String, Boolean> filterFunction = new Function<String, Boolean>() {
//            @Override
//            public Boolean apply(String s) throws Exception {
//                return true;
//            }
//        };

        Observable observable = Observable
                .fromArray(new String[] {"0", "1", "2", "3", "3", "4", "5", "6", "7"} )
                .skip(2)
                .take(3)
                .map(multiplyFunction)
                .map(filterFunction)
                .distinct()
                ;

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                String str = textViewDisplay.getText().toString();
                str = str + s;
                textViewDisplay.setText(str);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        findViewById(R.id.btnObservableOperators)
                .setOnClickListener(v -> observable.subscribe(observer));
    }



}