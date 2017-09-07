package com.study.java.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jeffrey
 * @since 2017/05/25 11:03
 */
public class RxTest {

    private static final Logger logger = LoggerFactory.getLogger(RxTest.class);

    @Test
    public void test1() {
        Observable<Integer> observable = Observable.create(observableEmitter -> {
            observableEmitter.onNext(1);
            observableEmitter.onNext(2);
            observableEmitter.onNext(3);
            observableEmitter.onComplete();
        });
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                logger.info("subscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                logger.info("next:" + integer);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                logger.error("error:" + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                logger.info("complete");
            }
        };
        observable.subscribe(observer);
    }

    @Test
    public void test2() {
        Observable.create((ObservableOnSubscribe<Integer>) observableEmitter -> {
            observableEmitter.onNext(1);
            observableEmitter.onNext(2);
            observableEmitter.onNext(3);
            observableEmitter.onComplete();
            observableEmitter.onNext(4);
        }).subscribe(new Observer<Integer>() {

            private Disposable disposable;

            private Integer count;

            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                logger.info("subscribe");
                this.disposable = disposable;
                this.count = 0;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                logger.info("next:" + integer);
                count++;
                if (count == 2) {
                    logger.info("dispose");
                    disposable.dispose();
                    logger.info("is dispose:" + disposable.isDisposed());
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                logger.error("error:" + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                logger.info("complete");
            }
        });
    }

    @Test
    public void test3() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                //这一步是必须，我们通常可以在这里做一些初始化操作，调用request()方法表示初始化工作已经完成
                //调用request()方法，会立即触发onNext()方法
                //在onComplete()方法完成，才会再执行request()后边的代码
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String value) {
                logger.info("onNext,{}", value);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("onError,{}", t.getMessage());
            }

            @Override
            public void onComplete() {
                //由于Reactive-Streams的兼容性，方法onCompleted被重命名为onComplete
                logger.info("onComplete,{}", "complete");
            }
        };

        Flowable.create((FlowableOnSubscribe<String>) e -> e.onNext("Hello,I am China!"),
            BackpressureStrategy.BUFFER)
            .subscribe(subscriber);
    }
}
