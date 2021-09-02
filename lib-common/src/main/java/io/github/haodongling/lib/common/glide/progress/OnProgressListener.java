package io.github.haodongling.lib.common.glide.progress;

import androidx.annotation.WorkerThread;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2018/9/17
 */
public interface OnProgressListener {
    @WorkerThread
    void onProgress(float progress);
}
