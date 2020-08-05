package ru.geekbrains.android2.examples_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    TextView textViewDisplay;
    Observable<String> observableJust;
    Observer<String> observerJust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDisplay = findViewById(R.id.textViewDisplay);

        // Пример 1. Стандарт
        observableJust = Observable.just("a", "b", "c", "d");

        observerJust = new Observer<String> () {
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


//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btnObservableJust:
//                observableJust.subscribe(observerJust);
//            case R.id.btnObservableCreate:
//                observableJust.subscribe(observerJust);
//
//            case R.id.btnObservf:
//                //mSubscription.unsubscribe();
//        }
//    }
}