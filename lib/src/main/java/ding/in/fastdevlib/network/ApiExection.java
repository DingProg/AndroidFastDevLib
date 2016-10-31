package ding.in.fastdevlib.network;

/**
 * fuction：网络请求异常处理类
 * Created by dingdegao on 2016/10/19.
 */
public class ApiExection extends RuntimeException {
    private ExceptionCode codeExceptionCode;
    private int code;
    private String msg;
    private String data;

    public ApiExection(ExceptionCode code) {
        this.codeExceptionCode = code;
    }

    public ApiExection(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiExection(String message, Throwable cause, ExceptionCode code) {
        super(message, cause);
        this.codeExceptionCode = code;
    }

    public ApiExection(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        if (codeExceptionCode != null) {
            return codeExceptionCode.getCode();
        }
        return code;
    }

    public String getMsg() {
        if (codeExceptionCode != null) {
            return codeExceptionCode.getMsg();
        }
        return msg;
    }

    public String getData() {
        return data;
    }

    public ExceptionCode getCodeExceptionCode() {
        return codeExceptionCode;
    }
}
