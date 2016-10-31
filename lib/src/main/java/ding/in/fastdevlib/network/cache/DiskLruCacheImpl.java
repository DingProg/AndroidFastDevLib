package ding.in.fastdevlib.network.cache;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ding.in.fastdevlib.network.utils.KeyMd5Utils;
import rx.Observable;
import rx.Subscriber;

/**
 * fuction：硬盘缓存
 * Created by dingdegao on 2016/10/28.
 */
public class DiskLruCacheImpl implements ICache {

    private static DiskLruCacheImpl diskLruCache = null;
    private static DiskLruCacheHelper diskLruCacheHelper;

    private String CACHE_DIR = "network_cache";
    private int MAXSIZE = 20 * 1024 * 1024;// 一个缓存文件最大可以缓存20M
    private Context mContext;

    //使用APP的Context，防止内存泄漏
    private DiskLruCacheImpl(Context context) {
        this.mContext=context;
    }

    public static DiskLruCacheImpl getInstance(Context context) {
        if (diskLruCache == null) {
            diskLruCache = new DiskLruCacheImpl(context);
        }
        return diskLruCache;
    }


    @Override
    public <T> Observable<T> get(final String key, final Class<T> cls) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                ObjectInputStream objectInputStream = null;
                try {
                    DiskLruCacheHelper disk = DiskLruCacheHelper.open(FileDirUtils.getCacheDir(mContext, CACHE_DIR), 1, 1, MAXSIZE);
                    if (disk != null) {
                        InputStream inputStream = disk.get(KeyMd5Utils.hashKey(key)).getInputStream(0);
                        if (inputStream != null) {
                            objectInputStream = new ObjectInputStream(inputStream);
                            Object obj = objectInputStream.readObject();
                            if (obj == null) {
                                subscriber.onNext(null);
                            } else {
                                subscriber.onNext((T) obj);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onNext(null);
                } finally {
                    if (objectInputStream != null) {
                        try {
                            objectInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            subscriber.onNext(null);
                        }
                    }
                    subscriber.onCompleted();
                }
            }
        });
    }

    @Override
    public <T> void put(String key, T t) {
        ObjectOutputStream oos = null;
        DiskLruCacheHelper.Editor editor=null;
        try {
            DiskLruCacheHelper diskLruCacheHelper = DiskLruCacheHelper.open(FileDirUtils.getCacheDir(mContext, CACHE_DIR), 1, 1, MAXSIZE);
            editor = diskLruCacheHelper
                    .edit(KeyMd5Utils.hashKey(key));
            if (editor != null) {
                oos = new ObjectOutputStream(editor.newOutputStream(0));
                oos.writeObject(t);
                editor.commit();
                diskLruCacheHelper.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(editor !=null){
                try {
                    editor.abort();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean isExpire() {
        return false;
    }

    @Override
    public boolean clear() {
        try {
            DiskLruCacheHelper.open(FileDirUtils.getCacheDir(mContext, CACHE_DIR), 1, 1, MAXSIZE).delete();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
