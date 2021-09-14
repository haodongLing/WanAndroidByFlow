package io.github.haodongling.lib.common.tasks;

import com.ycbjie.webviewlib.cache.WebCacheType;
import com.ycbjie.webviewlib.cache.WebViewCacheDelegate;
import com.ycbjie.webviewlib.cache.WebViewCacheWrapper;
import com.ycbjie.webviewlib.utils.X5LogUtils;
import com.ycbjie.webviewlib.utils.X5WebUtils;

import java.io.File;

import io.github.haodongling.lib.dispatcher.launcher.task.Task;

/**
 * Author: tangyuan
 * Time : 2021/8/18
 * Description:
 */
public class X5WebTask extends Task {
    @Override
    public void run() {
        // dex2oat优化方案
//        HashMap<String,Object> map = new HashMap<String, Object>();
//        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER,true);
//        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE,true);
//        QbSdk.initTbsSettings(map);
//
//        // 允许使用非wifi网络进行下载
//        QbSdk.setDownloadWithoutWifi(true);
//
//        // 初始化
//        QbSdk.initX5Environment(App.Companion.getCONTEXT(),new QbSdk.PreInitCallback() {
//
//            @Override
//            public void onCoreInitFinished() {
//                Log.d("ApplicationInit", " TBS X5 init finished");
//            }
//
//            @Override
//            public void onViewInitFinished(boolean b) {
//                Log.d("ApplicationInit", " TBS X5 init is $p0");
//            }
//        });
        X5WebUtils.init(mContext);
        X5LogUtils.setIsLog(true);
//        X5WebUtils.initCache(this);
//        WebViewCacheDelegate.getInstance().init(new WebViewCacheWrapper.Builder(this));


        //1.创建委托对象
        WebViewCacheDelegate webViewCacheDelegate = WebViewCacheDelegate.getInstance();
        //2.创建调用处理器对象，实现类
        WebViewCacheWrapper.Builder builder = new WebViewCacheWrapper.Builder(mContext);
        //设置缓存路径，默认getCacheDir，名称CacheWebViewCache
        builder.setCachePath(new File(mContext.getCacheDir().toString(),"CacheWebView"))
                //设置缓存大小，默认100M
                .setCacheSize(1024*1024*50)
                //设置本地路径
                //.setAssetsDir("yc")
                //设置http请求链接超时，默认20秒
                .setConnectTimeoutSecond(20)
                //设置http请求链接读取超时，默认20秒
                .setReadTimeoutSecond(20)
                //设置缓存为正常模式，默认模式为强制缓存静态资源
                .setCacheType(WebCacheType.NORMAL);
        webViewCacheDelegate.init(builder);
    }
}
