package io.github.haodongling.lib.common.glide.progress

import io.github.haodongling.lib.common.util.FFLog
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

/**
 * Author: tangyuan
 * Time : 2021/11/26
 * Description:
 */
class ProgressResponseBody(private val originResponseBody: ResponseBody, url: String) : ResponseBody() {
    private var mListener = ProgressInterceptor.getListener(url)
    private val bufferedSource = object : ForwardingSource(originResponseBody.source()) {
        private var totalBytesRead = 0L
        private var currentProgress = 0

        override fun read(sink: Buffer, byteCount: Long): Long {
            return super.read(sink, byteCount).apply {
                if (this == -1L) {
                    totalBytesRead = contentLength()
                } else {
                    totalBytesRead += this
                }
                val progress = (100F * totalBytesRead / contentLength()).toInt()
                FFLog.e("ProgressResponseBody", "download progress: $progress")
                if (progress != currentProgress) {
                    currentProgress = progress
                    mListener?.onProgress(currentProgress)
                }
                if (totalBytesRead == contentLength()) {
                    mListener = null
                }
            }
        }
    }.buffer()


    override fun contentLength(): Long {
        return originResponseBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return originResponseBody.contentType()
    }

    override fun source(): BufferedSource {
        return bufferedSource
    }
}