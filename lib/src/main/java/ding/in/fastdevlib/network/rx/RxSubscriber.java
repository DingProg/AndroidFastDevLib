package ding.in.fastdevlib.network.rx;

import android.net.ParseException;
import android.util.Log;

import ding.in.fastdevlib.network.utils.NLog;
import ding.in.fastdevlib.network.ApiExection;
import ding.in.fastdevlib.network.ExceptionCode;
import ding.in.fastdevlib.network.Strategy;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * fuction：观察者
 * Created by dingdegao on 2016/10/20.
 */
public class RxSubscriber<T> extends Subscriber<T> {

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


    private Strategy.Loading strategyLoading = new Strategy.Loading() {  //默认打开loading
        @Override
        public void openLoading() {
            //打开对话框
        }

        @Override
        public void closeLoading() {
            //关闭对话框
        }
    };

    private Strategy.TimeMonitor strategyTimeMonitor = new Strategy.TimeMonitor() { //默认开启性能监控
        long timeCount = 0;

        @Override
        public void start() {
            timeCount = System.currentTimeMillis();
        }

        @Override
        public void finish() {
            timeCount = System.currentTimeMillis() - timeCount;
        }

        @Override
        public long getTimeCount() {
            return timeCount;
        }
    };

    public void setStrategyLoading(Strategy.Loading strategyLoading) {
        this.strategyLoading = strategyLoading;
    }

    public void setStrategyTimeMonitor(Strategy.TimeMonitor strategyTimeMonitor) {
        this.strategyTimeMonitor = strategyTimeMonitor;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("info", "onStart");
        //非主线程
        if (strategyTimeMonitor != null) {
            strategyTimeMonitor.start();
        }
        if (strategyLoading != null) {
            strategyLoading.openLoading();
        }
    }

    @Override
    public void onCompleted() {
        if (strategyTimeMonitor != null) {
            strategyTimeMonitor.finish();
            NLog.i("onCompleted,加载时间:"+strategyTimeMonitor.getTimeCount()+"毫秒,"+strategyTimeMonitor.getTimeCount()/1000+"秒");
        }

        if (strategyLoading != null) {
            strategyLoading.closeLoading();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (strategyTimeMonitor != null) {
            strategyTimeMonitor.finish();
        }
        if (strategyLoading != null) {
            strategyLoading.closeLoading();
        }

        handleError(e);
        NLog.e(e.toString());
    }

    @Override
    public void onNext(T t) {
       // NLog.e("deal onNext:"+t.toString());
    }


    private void handleError(Throwable e) {
        if (e instanceof ApiExection) {//服务器返回错误
            ApiExection apiExection = (ApiExection) e;
            if (apiExection.getCode() == ExceptionCode.INVALID_TOKEN.getCode()) {

            } else if (apiExection.getCode() == ExceptionCode.LOGIN_VERIFICATION.getCode()) {

            } else if (apiExection.getCode() == ExceptionCode.UNPAID_ORDERS.getCode()) {

            } else if (apiExection.getCode() == ExceptionCode.FAILED.getCode()) {

            } else if (apiExection.getCode() == ExceptionCode.RETROFIT.getCode()) {

            }
        } else if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    //"网络错误";
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //"解析错误";
        } else if (e instanceof ConnectException) {
            //"连接失败";
        } else {
            //"未知错误";
        }
    }
}
