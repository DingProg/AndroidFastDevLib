package ding.in.fastdevlib.network.okhttpsettings;

import ding.in.fastdevlib.network.utils.NLog;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * fuction：
 * Created by dingdegao on 2016/10/19.
 */
public class LogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logRequest(request);
        Response response = chain.proceed(request);
        return logRespones(response);
    }

    private void logRequest(Request request) {
        StringBuilder logBuilder = new StringBuilder();
        if(request.url().toString().contains("=")){
            logBuilder.append(request.url().toString())
                    .append("&");
        }else {
            logBuilder.append(request.url().toString())
                    .append("?");
        }

        //Headers headers = request.headers(); 打印头信息
        RequestBody body = request.body();

        if (body != null && body instanceof FormBody) {
            FormBody formBody=(FormBody) body;
            for (int i = 0; i < formBody.size(); i++) {
                logBuilder.append(formBody.name(i))
                        .append("=")
                        .append(formBody.value(i))
                        .append("&");
            }
        } else {
            //文件信息不易打印
        }
        NLog.i(logBuilder.substring(0,logBuilder.length()-1));
    }

    private Response logRespones(Response response) {
        Response copy = response.newBuilder().build();
        ResponseBody body = copy.body();
        if (copy != null) {
            try {
                String bodyString = copy.body().string();
                NLog.i(copy.toString() + "-----" + copy.request().url().toString() + ":" + bodyString);
                body = ResponseBody.create(body.contentType(), bodyString);
                return response.newBuilder().body(body).build();
            } catch (IOException e) {
                NLog.e(e.toString());
            }
        }
        return response;
    }
}
