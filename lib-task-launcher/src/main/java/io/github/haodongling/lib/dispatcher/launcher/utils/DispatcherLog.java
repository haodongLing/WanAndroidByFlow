package io.github.haodongling.lib.dispatcher.launcher.utils;

import android.util.Log;

import io.github.haodongling.lib.dispatcher.launcher.BuildConfig;


/**
 * Author: tangyuan
 * Time : 2021/7/16
 * Description: 初始化专用Log
 */
public class DispatcherLog {

    private static boolean sDebug = BuildConfig.DEBUG;

    public static void i(String msg) {
        if (!sDebug) {
            return;
        }
        Log.i("TaskDispatcher",msg);
    }

    public static boolean isDebug() {
        return sDebug;
    }

    public static void setDebug(boolean debug) {
        sDebug = debug;
    }

}
