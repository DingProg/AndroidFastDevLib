package ding.in.fastdevlib.network.cache;

import android.support.v4.util.LruCache;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import ding.in.fastdevlib.network.utils.NLog;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import static ding.in.fastdevlib.network.utils.KeyMd5Utils.hashKey;

/**
 * fuction：内存缓存
 * Created by dingdegao on 2016/10/28.
 */
public class MemoryLruCacheImpl implements ICache {
    private static MemoryLruCacheImpl memoryLruCache = null;
    private LruCache<String, String> mCache;

    private MemoryLruCacheImpl() {
        final int maxMemory = (int) Runtime.getRuntime().maxMemory();
        final int cacheSize = maxMemory / 8;
        mCache = new LruCache<String, String>(cacheSize) {
            @Override
            protected int sizeOf(String key, String value) {
                try {
                    return value.getBytes("UTF-8").length;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return value.getBytes().length;
                }
            }
        };
    }

    public static MemoryLruCacheImpl getInstance() {
        if (memoryLruCache == null) {
            memoryLruCache = new MemoryLruCacheImpl();
        }
        return memoryLruCache;
    }

    @Override
    public <T> void put(String key, T t) {
        if (mCache != null) {
            mCache.put(hashKey(key), new Gson().toJson(t));
        }
    }

    @Override
    public boolean isExpire() {
        return false;
    }

    @Override
    public boolean clear() {
        return true;
    }

    @Override
    public <T> Observable<T> get(final String key, final Class<T> cls) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                String value = mCache.get(hashKey(key));
                if (value == null) {
                    subscriber.onNext(null);
                } else {
                    T t = new Gson().fromJson(value, cls);
                    subscriber.onNext(t);
                }
                subscriber.onCompleted();
            }
        });
    }
}
