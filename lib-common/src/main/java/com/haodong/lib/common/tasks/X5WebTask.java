package com.haodong.lib.common.tasks;

import android.util.Log;

import com.haodong.lib.common.App;
import com.haodong.lib.dispatcher.launcher.task.Task;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

/**
 * Author: tangyuan
 * Time : 2021/8/18
 * Description:
 */
public class X5WebTask extends Task {
    @Override
    public void run() {
        // dex2oat优化方案
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER,true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE,true);
        QbSdk.initTbsSettings(map);

        // 允许使用非wifi网络进行下载
        QbSdk.setDownloadWithoutWifi(true);

        // 初始化
        QbSdk.initX5Environment(App.Companion.getCONTEXT(),new QbSdk.PreInitCallback() {

            @Override
            public void onCoreInitFinished() {
                Log.d("ApplicationInit", " TBS X5 init finished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.d("ApplicationInit", " TBS X5 init is $p0");
            }
        });
    }
}
