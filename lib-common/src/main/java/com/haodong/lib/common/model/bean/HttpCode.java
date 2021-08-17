package com.haodong.lib.common.model.bean;

/**
 * Author: tangyuan
 * Time : 2021/8/16
 * Description:
 */
public class HttpCode {

    private int code;//如果有业务code，返回业务code,否则返回http错误编码
    private String errorMsg; //服务端可能会传回的错误信息(不一定存在)
    private String cache;//缓存
    private int httpCode;//http状态码

    public HttpCode() {
        errorMsg = HttpEnum.HTTP_ERROR_DEFAULT.getMessage();
        code = HttpEnum.HTTP_ERROR_DEFAULT.getCode();
    }

    public HttpCode(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getErrCode() {
        return code;
    }

    public void setErrCode(int errCode) {
        this.code = errCode;
    }

    public String getErrMessage() {
        return errorMsg;
    }

    public String getError() {
        return errorMsg;
    }

    public void setError(String error) {
        this.errorMsg = error;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    @Override public String toString() {
        return "HttpCode{"
                + "errCode="
                + code
                + ", errMessage='"
                + errorMsg
                + '\''
                + ", error='"
                + errorMsg
                + '\''
                + ", cache='"
                + cache
                + '\''
                + '}';
    }

    public enum HttpEnum {
        HTTP_ERROR_NO_MSG(10000, ""),
        HTTP_RESPONSE_ERROR(20000, "返回数据为null"),
        HTTP_ERROR_SERVER(20001, "服务器出错"),
        HTTP_ERROR_CONNECT(20002, "网络开小差"),
        HTTP_ERROR_REQUEST(20003, "请求出错"),
        HTTP_ERROR_TIMEOUT(20004, "链接超时"),
        HTTP_ERROR_EXCEPTION(20005, "请求的异常"),
        HTTP_ERROR_DEFAULT(10001, "未知错误"),
        HTTP_TOKEN_EXPIRED(910000, "用户登录状态异常,重新登录"),//910000
        HTTP_UTOKEN_EXPIRED(910001, "用户登录状态异常,重新登录"),//910001
        HTTP_MEMBER_NOT_REGISTER(10003, "账号未注册"),
        HTTP_GRAPHIC_CAPTCHA(11020, ""),
        HTTP_GRAPHIC_CAPTCHA_ERROR(11010, ""),
        ERROR_JSON(3000, "Json解析失败");

        private int code;//业务码
        private String message;

        HttpEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }
}
