package ding.in.fastdevlib.network;

/**
 * fuction：网络异常代码
 * Created by dingdegao on 2016/10/19.
 */
public enum ExceptionCode {

    SUCCESS(200, "网络请求成功"),
    FAILED(210, "网络请求失败"),
    INVALID_TOKEN(502, "用户被挤掉"),
    LOGIN_VERIFICATION(100, "登录验证"),
    UNPAID_ORDERS(102, "支付验证"),
    RETROFIT(-1, "解析过程异常");

    private int code;
    private String msg;

    ExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
