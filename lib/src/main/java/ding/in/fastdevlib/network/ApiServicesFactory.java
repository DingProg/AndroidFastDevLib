package ding.in.fastdevlib.network;

import ding.in.fastdevlib.network.okhttpsettings.NetOkHttpClient;

import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * fuction：api 工厂
 * Created by dingdegao on 2016/10/19.
 */
public class ApiServicesFactory {
    private static ApiServicesFactory INSTANCE = null;

    private Retrofit retrofit;
    private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();

    private ApiServicesFactory() {
        init();
    }

    public static ApiServicesFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (ApiServicesFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiServicesFactory();
                }
            }
        }
        return INSTANCE;
    }


    private void init() {
        retrofit = new Retrofit.Builder().baseUrl("http://www.tngou.net/api/")
                .client(NetOkHttpClient.initOkHttp())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public <T> T produceApi(Class<T> cls) {
        return getService(cls, null);
    }

    public <T> T produceApi(Class<T> cls, String newBaseUrl) {
        return getService(cls, newBaseUrl);
    }

    private <T> T getService(Class<T> cls, String newBaseUrl) {
        if (map.get(cls.getName()) == null) {
            if(newBaseUrl == null){
                map.put(cls.getName(), createService(cls, newBaseUrl));
            }else{
                map.put(cls.getName() + newBaseUrl, createService(cls, newBaseUrl));
            }
        }
        return (T) map.get(cls.getName());
    }

    private <T> T createService(Class<T> cls, String newBaseUrl) {
        if (newBaseUrl != null) {
            retrofit = new Retrofit.Builder().baseUrl(newBaseUrl)
                    .client(NetOkHttpClient.initOkHttp())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(cls);
        } else {
            return retrofit.create(cls);
        }
    }

}
