package io.github.haodongling.lib.utils;

import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;import io.github.haodongling.lib.utils.Utils;

import java.io.File;

/**
 * @author CuiZhen
 * @date 2019/11/10
 * GitHub: https://github.com/goweii
 */
public class UriUtils {

    public static Uri getFileUri(File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(Utils.getAppContext(), Utils.getAppContext().getPackageName() + ".file.path.share", file);
        }
        return Uri.fromFile(file);
    }
}
