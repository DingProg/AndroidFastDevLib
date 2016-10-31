package ding.in.fastdevlib.network.okhttpsettings;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * fuction：
 * Created by dingdegao on 2016/10/19.
 */
public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
//        Response originalResponse = chain.proceed(chain.request());
//        return originalResponse.newBuilder().removeHeader("Pragma")
//                .header("Cache-Control", String.format("max-age=%d", 10)).build();//设置了缓存时间为10秒
        Response originalResponse = chain.proceed(chain.request());
        //使用@Headers指定的缓存请求头
        String cacheControl = chain.request().cacheControl().toString();
        return originalResponse.newBuilder()
                .header("Cache-Control", cacheControl)
                .build();
    }
}
