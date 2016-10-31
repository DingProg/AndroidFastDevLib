package ding.in.fastdevlib.network.requestrwapper;

import ding.in.fastdevlib.network.ApiExection;
import ding.in.fastdevlib.network.ExceptionCode;
import ding.in.fastdevlib.network.rx.RxSubscriber;

/**
 * fuction：
 * Created by dingdegao on 2016/10/20.
 */
public class RxSubscriberWrapper<T> extends RxSubscriber<T> {

    private SimpleRequestListener<T> requestListener;

    public RxSubscriberWrapper(SimpleRequestListener<T> requestListener) {
        this.requestListener = requestListener;
    }

    public void setRequestListener(SimpleRequestListener requestListener) {
        this.requestListener = requestListener;
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (requestListener != null) {
            if (e instanceof ApiExection) {
                ApiExection apiExection = (ApiExection) e;
                //apiExection.getMsg() 有可能为空
                requestListener.requestFail(apiExection.getData(), apiExection.getCode(), apiExection.getMsg());
            } else {
                requestListener.requestFail("", ExceptionCode.FAILED.getCode(), ExceptionCode.FAILED.getMsg());
            }
        }
    }


    @Override
    public void onNext(T t) {
        super.onNext(t);
        if (requestListener != null) {
            requestListener.requestSuccess(t);
        }
    }
}
