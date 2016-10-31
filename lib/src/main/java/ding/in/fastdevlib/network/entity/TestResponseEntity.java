package ding.in.fastdevlib.network.entity;

/**
 * fuction：
 * Created by dingdegao on 2016/10/20.
 */
public class TestResponseEntity<T> {
    private boolean status;
    private T tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getTngou() {
        return tngou;
    }

    public void setTngou(T tngou) {
        this.tngou = tngou;
    }
}
