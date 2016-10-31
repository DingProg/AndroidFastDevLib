package ding.in.fastdevlib.network.cache;

import rx.Observable;

/**
 * fuction：
 * Created by dingdegao on 2016/10/28.
 */
public interface ICache{

    /**
     * 获取缓存
     */
    <T> Observable<T> get(String key,Class<T> cls);

    /**
     * put数据
     */
    <T> void put(String key,T t);

    /**
     * 是否过期
     */
    boolean isExpire();

    /**
     * 清除缓存
     */
    boolean clear();

}
