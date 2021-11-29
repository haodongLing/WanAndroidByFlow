package io.github.haodongling.lib.common.glide.progress;

import androidx.annotation.WorkerThread;

public interface OnProgressListener {
    @WorkerThread
    void onProgress(float progress);
}
