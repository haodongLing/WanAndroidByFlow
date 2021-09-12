package io.github.haodongling.lib.webview.utils

import android.util.Log
import com.tencent.smtt.sdk.BuildConfig

/**
 * Author: tangyuan
 * Time : 2021/9/10
 * Description:
 */
class X5LogUtils {
    companion object {
        var isLog = BuildConfig.DEBUG
            set(value) {
                field = value
            }
        const val TAG = "X5LogUtils"

        @JvmStatic
        open fun d(message: String) {
            isLog.let {
                Log.d(TAG, message)
            }
        }

        @JvmStatic
        open fun i(message: String) {
            isLog.let {
                Log.i(TAG, message)
            }
        }


        @JvmStatic
        open fun e(message: String, throwable: Throwable?=null) {
            isLog.let {
               if (throwable==null){
                   Log.d(TAG, message)
               }else{
                   Log.e(TAG,message,throwable)
               }

            }
        }
    }


}