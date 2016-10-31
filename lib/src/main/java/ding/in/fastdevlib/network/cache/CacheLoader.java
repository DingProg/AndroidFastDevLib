package ding.in.fastdevlib.network.cache;

import android.app.Application;

import ding.in.fastdevlib.network.entity.TestResponseEntity;
import ding.in.fastdevlib.network.rx.TestRxDataResponseMap;
import ding.in.fastdevlib.network.utils.NLog;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * fuction：
 * Created by dingdegao on 2016/10/28.
 */
public class CacheLoader {

    private static CacheLoader cacheLoader;

    private ICache mMemoryCache;
    private ICache mDiskCache;

    private CacheLoader(Application application) {
        mMemoryCache=MemoryLruCacheImpl.getInstance();
        mDiskCache=DiskLruCacheImpl.getInstance(application.getApplicationContext());
    }

    /**
     * 防止内存泄漏，所以直接限制为Application
     */
    public static CacheLoader getInstance(Application application){
        if(cacheLoader == null){
            cacheLoader=new CacheLoader(application);
        }
        return cacheLoader;
    }



    public <T> Observable<T> dealCache(String key, Class<T> cls, Observable<TestResponseEntity<T>> netObservable) {
        return Observable.concat(memory(key,cls)
                , disk(key,cls)
                , network(key,netObservable))
                .first(new Func1<T, Boolean>() {
                    @Override
                    public Boolean call(T t) {
                        return t != null;
                    }
                });
    }


    private <T> Observable<? extends T> memory(String key, Class<T> cls) {
        return mMemoryCache.get(key,cls).subscribeOn(Schedulers.io()).doOnNext(new Action1<T>() {
            @Override
            public void call(T t) {
                if(t != null){
                    NLog.i("来自内存缓存");
                }
            }
        });
    }

    private <T> Observable<? extends T> disk(final String key, Class<T> cls) {
        return mDiskCache.get(key,cls).subscribeOn(Schedulers.io()).doOnNext(new Action1<T>() {
            @Override
            public void call(final T t) {
                if(t != null){
                    NLog.i("来自硬盘缓存");
                    Schedulers.io().createWorker().schedule(new Action0() {
                        @Override
                        public void call() {
                            mMemoryCache.put(key,t);
                        }
                    });
                }
            }
        });
    }

    private <T> Observable<T> network(final String key, Observable<TestResponseEntity<T>> observable) {
        return observable
                .map((Func1<? super TestResponseEntity<T>, ? extends T>) new TestRxDataResponseMap<T>())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(final T t) {
                        Schedulers.io().createWorker().schedule(new Action0() {
                            @Override
                            public void call() {
                                if(t != null){
                                    NLog.i("来自网络");
                                    mMemoryCache.put(key,t);
                                    mDiskCache.put(key,t);
                                }
                            }
                        });
                    }
                });
    }
}
