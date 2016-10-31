package ding.in.fastdevlib.network.requestrwapper;

import ding.in.fastdevlib.network.ApiServicesFactory;
import ding.in.fastdevlib.network.entity.TestResponseEntity;
import ding.in.fastdevlib.network.requestrwapper.api.EBizApi;
import ding.in.fastdevlib.network.requestrwapper.api.EHomeApi;
import ding.in.fastdevlib.network.requestrwapper.api.EPayApi;
import ding.in.fastdevlib.network.rx.RxHelper;


import rx.Observable;
import rx.Subscription;

/**
 * fuction：
 * Created by dingdegao on 2016/10/19.
 */
public class RequestManager {

    //基本内容:返回数据统一处理，retrofit (okhttp)处理，rx封装处理
    //缓存处理, 策略模式...过期策略
    //log处理
    //性能监控
    //loading处理

    /**
     * 流程封装模板
     * //发起请求
     * //处理一些具体的事 代理缓存策略
     * //回调处理
     */

    public static EBizApi getEBizApi() {
        EBizApi eBizApi = ApiServicesFactory.getInstance().produceApi(EBizApi.class);
        return eBizApi;
    }

    public static EHomeApi getEHomeApi() {
        EHomeApi eHomeApi = ApiServicesFactory.getInstance().produceApi(EHomeApi.class);
        return eHomeApi;
    }

    public static EPayApi getEPayApi() {
        EPayApi ePayApi = ApiServicesFactory.getInstance().produceApi(EPayApi.class);
        return ePayApi;
    }

    public static EBizApi getEBizApiNewHost() {
        EBizApi eBizApi = ApiServicesFactory.getInstance().produceApi(EBizApi.class, "");
        return eBizApi;
    }

    public static EHomeApi getEHomeApiNewHost() {
        EHomeApi eHomeApi = ApiServicesFactory.getInstance().produceApi(EHomeApi.class, "");
        return eHomeApi;
    }

    public static EPayApi getEPayApiNewHost() {
        EPayApi ePayApi = ApiServicesFactory.getInstance().produceApi(EPayApi.class, "");
        return ePayApi;
    }


    public static <T> Subscription dealRequest(Observable<TestResponseEntity<T>> observable, SimpleRequestListener<T> requestListener) {
        return RxHelper.deal(observable, new RxSubscriberWrapper<T>(requestListener));
    }
}
