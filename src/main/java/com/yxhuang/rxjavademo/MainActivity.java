package com.yxhuang.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG  = "rxJava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button start = (Button) findViewById(R.id.btn_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                testTimer();
//                testInterval();
//                testThrottleFirst(start);
//                testSchedulePeriodically();
                test();
            }
        });

    }

    // timer  延迟执行， TimeUit 可选时间单位
    private void testTimer(){
        Log.i(TAG, "start");

        Observable.timer(5, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i(TAG, "onNext");
                    }
                });
    }

    // 周期性执行，例子是每隔五秒执行一次
    private void testInterval(){
        Log.i(TAG, "start");
        Observable.interval(5, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i(TAG, "hello word");
                    }
                });
    }

    // 防止重复点击按钮
    private void testThrottleFirst(View view){
        Log.i(TAG, "start");
        RxView.clicks(view)   // RxView 需要 RxBinding
                .throttleLast(1, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {
                        Log.i(TAG, "hello word");
                    }
                });

    }

    // schedulePeriodically 轮询
    private void testSchedulePeriodically(){
        Log.i(TAG, "start");
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                Schedulers.newThread().createWorker()
                        .schedulePeriodically(new Action0() {
                            @Override
                            public void call() {
                                    subscriber.onNext("schedulePeriodically");
                            }
                        }, 0, 5, TimeUnit.SECONDS);   // 0 初始延迟， 5 间隔时间
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "schedule periodically :  " + s);
            }
        });
    }

    // 对 list , map 的遍历
    private void test(){
        String[] names = {"aljl", "addg", "egeg", "oiinjj", "jliooj"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, "name: " + s);
                    }
                });
    }


}
