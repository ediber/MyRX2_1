package com.edibershatsky.myrx2_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";

    @BindView(R.id.button1) View button1;
    @BindView(R.id.subscribe_again) View subscribeAgain;

    private Observable<Integer> observable1;
    private Observer<Integer> observer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        button1.setOnClickListener(new Button1Listener());
        subscribeAgain.setOnClickListener(new SubscribeAgainListener());
    }

    private class Button1Listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            // creates observabale, creates observer, observer subscribes to observabale
            observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
                                                                   @Override

                                                                   public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                                                                       //Use onNext to emit each item in the stream//
                                                                       e.onNext(1);
                                                                       e.onNext(2);
                                                                       e.onNext(3);
                                                                       e.onNext(4);

                                                                       //Once the Observable has emitted all items in the sequence, call onComplete//
                                                                       e.onComplete();
                                                                   }
                                                               }
            );

            observer1 = new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.e(TAG, "onSubscribe: ");
                }

                @Override
                public void onNext(Integer value) {
                    Log.e(TAG, "onNext: " + value);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "onError: ");
                }

                @Override
                public void onComplete() {
                    Log.e(TAG, "onComplete: All Done!");
                }
            };

//Create our subscription//
            observable1.subscribe(observer1);
        }
    }


    private class SubscribeAgainListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            observable1.subscribe(observer1);
        }
    }
}
