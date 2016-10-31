package ding.in.fastdevlib.network.rx;

import android.app.Application;

import ding.in.fastdevlib.network.cache.CacheLoader;
import ding.in.fastdevlib.network.entity.TestResponseEntity;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * fuction：
 * Created by dingdegao on 2016/10/19.
 */
public class RxHelper {

    private static <T> Observable<T> commonDeal(Observable<TestResponseEntity<T>> observable){
        return observable
                .map((Func1<? super TestResponseEntity<T>, ? extends T>) new TestRxDataResponseMap<T>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 处理订阅
     *
     * @param observable api 里面的事件源
     * @param subscriber 观察者，回调
     * @param <T>        回掉的泛型
     * @return 返回订阅的联系
     */
    public static <T> Subscription deal(final Observable<TestResponseEntity<T>> observable, RxSubscriber<T> subscriber) {
        return commonDeal(observable)
                .retryWhen(new RxRetryPolicy())
                .subscribe(subscriber);
    }

    /**
     * 没有重试策略的请求
     *
     * @param observable api 里面的事件源
     * @param subscriber 观察者，回调
     * @param <T>        回掉的泛型
     * @return 返回订阅的联系
     */
    public static <T> Subscription dealOnlyOnce(Observable<TestResponseEntity<T>> observable, RxSubscriber<T> subscriber) {
        return commonDeal(observable)
                .subscribe(subscriber);
    }

    /**
     * 处理订阅
     *
     * @param <T>          回掉的泛型
     * @param observable   api 里面的事件源
     * @param rxSubscriber
     * @return 返回订阅的联系
     */
    public static <T> Subscription dealTest(Observable<TestResponseEntity<T>> observable, RxSubscriber<T> rxSubscriber) {
        return commonDeal(observable)
                .subscribe((Observer<? super T>) rxSubscriber);
    }

    public static <T> Subscription dealWithCache(String key, Class<T> cls, Application application,
                                                 Observable<TestResponseEntity<T>> network,
                                                 RxSubscriber<T> rxSubscriber){
        return CacheLoader.getInstance(application).dealCache(key,cls,network)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(rxSubscriber);
    }


    /**
     * 解除绑定 取消网络请求
     *
     * @param subscription 订阅的联系
     */
    public static void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public static <T> Subscription dealZip() {
        return null;
    }

    public static <T> Subscription dealFlatMap() {
        Observable observable = null;
        observable.flatMap(new Func1() {
            @Override
            public Object call(Object o) {
                return null;
            }
        });
        return null;
    }

}
