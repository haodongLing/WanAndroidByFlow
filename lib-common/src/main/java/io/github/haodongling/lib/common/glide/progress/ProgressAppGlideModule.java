package io.github.haodongling.lib.common.glide.progress;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.Excludes;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import androidx.annotation.NonNull;
import io.github.haodongling.lib.common.util.FFLog;
import okhttp3.OkHttpClient;


@GlideModule
@Excludes(value ={OkHttpLibraryGlideModule.class})
public class ProgressAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(getOkHttpClient()));
        FFLog.d("ProgressAppGlideModule", "registerComponents down");
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }


    private static OkHttpClient getOkHttpClient() {
        FFLog.d("ProgressAppGlideModule", "getOkHttpClient");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ProgressInterceptor());
        FFLog.d("ProgressAppGlideModule", "getOkHttpClient down");
        return builder.build();
    }
}
