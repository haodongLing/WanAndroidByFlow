package com.haodong.lib.common.net.intercepter;

import com.haodong.lib.common.util.FFLog;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Author: tangyuan
 * Time : 2021/8/17
 * Description:
 */
public class ResponseInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response response=chain.proceed(chain.request());
        FFLog.i("reponse-->"+response.toString());
//        try {
//            if (response.body()!=null){
//                BufferedSource source = response.body().source();
//                Buffer buffer = source.buffer();
//                Charset UTF8 = Charset.forName("UTF-8");
//                String bodyStr = buffer.clone().readString(UTF8);
//                JSONObject jsonObject = new JSONObject(bodyStr);
//                FFLog.i("errorCode-->"+jsonObject.optInt("errorCode    ")+jsonObject.optString("errorMsg"," "));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return response;
    }
}
