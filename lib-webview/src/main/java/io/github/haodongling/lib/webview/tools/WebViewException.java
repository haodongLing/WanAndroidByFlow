package io.github.haodongling.lib.webview.tools;

/**
 * <pre>
 *     @author  yangchong
 *     email  : yangchong211@163.com
 *     time   : 2020/04/27
 *     desc   : 自定义异常
 *     revise:
 * </pre>
 */
public class WebViewException extends RuntimeException {

    private int mCode = 0;

    public WebViewException(int code, String msg) {
        super(msg);
        mCode = code;
    }

    public WebViewException(String msg) {
        super(msg);
    }

    public WebViewException(Throwable throwable) {
        super(throwable);
        if (throwable instanceof WebViewException) {
            mCode = ((WebViewException) throwable).getCode();
        }
    }

    public int getCode() {
        return mCode;
    }

    public String getMsg() {
        return getMessage();
    }
}
