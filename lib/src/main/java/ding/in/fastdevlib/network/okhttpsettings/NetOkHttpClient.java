package ding.in.fastdevlib.network.okhttpsettings;

import android.os.Environment;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * fuction：
 * Created by dingdegao on 2016/10/19.
 */
public class NetOkHttpClient {

    public static final long CONNECT_TIME = 30 * 1000; //超时时间
    private static final long READ_TIEM = 30 * 1000;//读取超时时间
    private static String cachedirectory = Environment.getExternalStorageDirectory() + "/ecej/caches";
    private static Cache cache = new Cache(new File(cachedirectory), 20 * 1024 * 1024);//20M缓存

    private static OkHttpClient okHttpClient;

    public static OkHttpClient initOkHttp() {
        // HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.getSslSocketFactory(null, null, null);
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(false)//失败重连
                //.addNetworkInterceptor()
                .addInterceptor(new LogInterceptor())
                .addInterceptor(new CacheInterceptor())
                .cache(cache)
                //.sslSocketFactory(sslSocketFactory.sSLSocketFactory,sslSocketFactory.trustManager)
                .readTimeout(READ_TIEM, TimeUnit.MILLISECONDS)
                .build();

        return okHttpClient;
    }

}
