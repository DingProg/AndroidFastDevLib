package ding.in.fastdevlib.network.requestrwapper;

/**
 * fuction：
 * Created by dingdegao on 2016/10/21.
 */
public interface SimpleRequestListener<T> {

    /**
     * @param t 返回的解析好的数据
     */
    void requestSuccess(T t);

    /**
     * @param json     返回的json数据
     * @param typeCode 异常类型code
     * @param msg      异常消息
     */
    void requestFail(String json, int typeCode, String msg);
}
