package io.github.haodongling.kotlinmvvmdemo.web

import android.animation.Animator
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.*
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.dialog.ImagePreviewDialog
import io.github.haodongling.kotlinmvvmdemo.model.api.RetrofitClient
import io.github.haodongling.kotlinmvvmdemo.web.js.JsInjector
import io.github.haodongling.kotlinmvvmdemo.widget.WebContainer
import io.github.haodongling.lib.common.util.FFLog
import io.github.haodongling.lib.utils.global.AppGlobals
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import okhttp3.Cookie
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import per.goweii.anylayer.DecorLayer
import per.goweii.anylayer.Layer.AnimatorCreator
import java.lang.Exception

/**
 * Author: tangyuan
 * Time : 2021/9/28
 * Description:
 */
class WebHolder(activity: Activity, container: WebContainer, progressBar: ProgressBar?) {
    companion object {
        const val TAG = "WebHolder"
        @JvmStatic
        fun syncCookiesForWanAndroid(url: String) {
            if (TextUtils.isEmpty(url)) {
                return
            }
            val host = Uri.parse(url).host
            if (!TextUtils.equals(host, "www.wanandroid.com")) {
                return
            }
            val cookies: List<Cookie> = RetrofitClient.mCookieJar.loadForRequest(url.toHttpUrl())
            if (cookies == null || cookies.isEmpty()) {
                return
            }
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.removeSessionCookie()
                cookieManager.removeExpiredCookie()
            } else {
                cookieManager.removeSessionCookies(null)
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                for (cookie in cookies) {
                    cookieManager.setCookie(url, cookie.name + "=" + cookie.value)
                }
                CookieSyncManager.createInstance(AppGlobals.getApplication())
                CookieSyncManager.getInstance().sync()
            } else {
                for (cookie in cookies) {
                    cookieManager.setCookie(url, cookie.name + "=" + cookie.value)
                }
                cookieManager.flush()
            }
        }
    }

    private var mOnPageScrollEndListener: OnPageScrollEndListener? = null
    private lateinit var mOnPageTitleCallback: OnPageTitleCallback
    private lateinit var mOnPageLoadCallback: OnPageLoadCallback
    private lateinit var mOnPageProgressCallback: OnPageProgressCallback
    private lateinit var mOnHistoryUpdateCallback: OnHistoryUpdateCallback
    private lateinit var mOverrideUrlInterceptor: OverrideUrlInterceptor
    private lateinit var mInterceptUrlInterceptor: InterceptUrlInterceptor

    lateinit var mActivity: Activity
    private lateinit var mWebContainer: WebContainer
    private lateinit var mWebView: WebView
    private lateinit var mProgressBar: ProgressBar
    private var mUserAgentString: String

    private var allowOpenOtherApp = true
    private var allowOpenDownload = true
    private var allowRedirect = true

    private lateinit var jsInjector: JsInjector

    private var useInstanceCache = false
    private var isProgressShown = false
    private var isPageScrollEnd = false

    init {
        activity.window.setFormat(PixelFormat.TRANSLUCENT)
        mActivity = activity
        mWebContainer = container
        mWebContainer.setBackgroundColor(mActivity.resources.getColor(R.color.white))
        if (progressBar == null) {
            mProgressBar = LayoutInflater.from(activity)
                .inflate(R.layout.basic_ui_progress_bar, container, false) as MaterialProgressBar
            container.addView(
                mProgressBar, FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    activity.resources.getDimensionPixelSize(R.dimen.basic_ui_action_bar_loading_bar_height)
                )
            )
        } else {
            mProgressBar = progressBar
        }
        mProgressBar?.max = 100
        if (useInstanceCache) {
            mWebView = WebInstance.getInstance(mActivity).obtain()
        } else {
            mWebView = WebInstance.getInstance(mActivity).create()
        }
        container.addView(
            mWebView,
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        )
        mProgressBar?.let {
            mProgressBar
        }
        mWebView.webChromeClient = WanWebChromeClient()
        mWebView.webViewClient = WanWebViewClient()
        mWebView.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
            FFLog.i("onDownloadStart:url=$url")
            FFLog.i("onDownloadStart:userAgent=$userAgent")
            FFLog.i("onDownloadStart:contentDisposition=$contentDisposition")
            FFLog.i("onDownloadStart:mimeType=$mimeType")
            FFLog.i("onDownloadStart:contentLength=$contentLength")
            if (!allowOpenDownload) return@DownloadListener
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse(url)
                mWebView.context.startActivity(intent)
            } catch (ignore: Exception) {
            }
        })
        mWebView.setOnLongClickListener {
            val hitTestResult = mWebView.hitTestResult
            val result = HitResult(hitTestResult)
            when (result.getType()) {
                HitResult.Type.IMAGE_TYPE, HitResult.Type.IMAGE_ANCHOR_TYPE, HitResult.Type.SRC_IMAGE_ANCHOR_TYPE -> {
                    ImagePreviewDialog(mActivity, result.getResult()).show()
                    true
                }
                else -> false
            }
        }

        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            QbSdk.forceSysWebView()
        } else {
            QbSdk.unForceSysWebView()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mWebView.setOnScrollChangeListener(View.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (mOnPageScrollEndListener == null) return@OnScrollChangeListener
                if (isProgressShown) return@OnScrollChangeListener
                if (isPageScrollEnd) return@OnScrollChangeListener
                val contentHeight = mWebView.contentHeight * mWebView.scale
                val webViewHeight = mWebView.height.toFloat()
                if (scrollY + webViewHeight >= contentHeight - 120) {
                    isPageScrollEnd = true
                    mOnPageScrollEndListener!!.onPageScrollEnd()
                }
            })
        }
        val webSetting = mWebView.settings
        mUserAgentString = webSetting.userAgentString
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            val v = mWebView.view
            if (v is android.webkit.WebView) {
                val wv = v
                WebSettingsCompat.setForceDark(wv.settings, WebSettingsCompat.FORCE_DARK_OFF)
            }
        } else {
            mWebView.setDayOrNight(true)
        }
        jsInjector = JsInjector(mWebView)
        jsInjector.attach()
    }

    fun loadUrl(url: String): WebHolder {
        mWebView.loadUrl(url)
        return this@WebHolder
    }

    fun getUrl(): String {
        val url: String? = mWebView.url;
        return url ?: ""
    }

    fun getTitle(): String {
        val title: String? = mWebView.title
        return title ?: ""
    }

    fun getUserAgent(): String {
        return mUserAgentString
    }

    fun canGoBack(): Boolean {
        return mWebView.canGoBack()
    }

    fun canGoforward(): Boolean {
        return mWebView.canGoForward()
    }

    fun canGoBackOrForward(steps: Int): Boolean {
        return mWebView.canGoBackOrForward(steps)
    }

    public fun goBack() {
        mWebView.goBack()
    }

    public fun goForward() {
        mWebView.goForward()
    }

    public fun goBackOrForward(steps: Int) {
        mWebView.goBackOrForward(steps)
    }

    public fun reload() {
        mWebView.stopLoading()
    }

    fun onResume() {
        mWebView.resumeTimers()
        mWebView.onResume()
    }

    fun onPause() {
        mWebView.pauseTimers()
        mWebView.onPause()
    }

    fun onDestroy(destroyOrRecycle: Boolean) {
        jsInjector.detach()
        mProgressBar!!.clearAnimation()
        if (useInstanceCache) {
            if (destroyOrRecycle) {
                WebInstance.getInstance(mActivity).destroy(mWebView)
            } else {
                WebInstance.getInstance(mActivity).recycle(mWebView)
            }
        } else {
            WebInstance.getInstance(mActivity).destroy(mWebView)
        }
    }

    fun handleKeyEvent(keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false
        }
        if (mWebView.canGoBack()) {
            mWebView.goBack()
            return true
        }
        return false
    }

    fun setLoadCacheElseNetwork(loadCacheElseNetwork: Boolean): WebHolder {
        val webSetting = mWebView.settings
        if (loadCacheElseNetwork) {
            webSetting.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        } else {
            webSetting.cacheMode = WebSettings.LOAD_DEFAULT
        }
        return this
    }

    fun setUseInstanceCache(useInstanceCache: Boolean): WebHolder {
        this.useInstanceCache = useInstanceCache
        return this
    }

    fun setAllowOpenOtherApp(allowOpenOtherApp: Boolean): WebHolder {
        this.allowOpenOtherApp = allowOpenOtherApp
        return this
    }

    fun setAllowOpenDownload(allowOpenDownload: Boolean): WebHolder {
        this.allowOpenDownload = allowOpenDownload
        return this
    }

    fun setAllowRedirect(allowRedirect: Boolean): WebHolder {
        this.allowRedirect = allowRedirect
        return this
    }

    fun setOnPageTitleCallback(onPageTitleCallback: OnPageTitleCallback): WebHolder {
        mOnPageTitleCallback = onPageTitleCallback
        return this

    }

    fun setOnPageScrollEndListener(onPageScrollEndListener: OnPageScrollEndListener): WebHolder {
        mOnPageScrollEndListener = onPageScrollEndListener
        return this
    }

    fun setOnPageLoadCallback(onPageLoadCallback: OnPageLoadCallback): WebHolder {
        mOnPageLoadCallback = onPageLoadCallback
        return this
    }

    fun setOnPageProgressCallback(onPageProgressCallback: OnPageProgressCallback): WebHolder {
        mOnPageProgressCallback = onPageProgressCallback
        return this
    }

    fun setOnHistoryUpdateCallback(onHistoryUpdateCallback: OnHistoryUpdateCallback): WebHolder {
        mOnHistoryUpdateCallback = onHistoryUpdateCallback
        return this
    }

    fun setOverrideUrlInterceptor(overrideUrlInterceptor: OverrideUrlInterceptor): WebHolder {
        mOverrideUrlInterceptor = overrideUrlInterceptor
        return this
    }

    fun setInterceptUrlInterceptor(interceptUrlInterceptor: InterceptUrlInterceptor): WebHolder {
        mInterceptUrlInterceptor = interceptUrlInterceptor
        return this
    }

    inner class WanWebChromeClient : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            if (mOnPageTitleCallback != null) {
                mOnPageTitleCallback.onReceivedTitle(title)
            }
        }

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            isPageScrollEnd = false
            jsInjector.onProgressChanged(newProgress)
            if (newProgress < 30) {
                if (!isProgressShown) {
                    isProgressShown = true
                    onShowProgress()
                }
                onProgressChanged(newProgress)
            } else if (newProgress > 80) {
                onProgressChanged(newProgress)
                if (isProgressShown) {
                    isProgressShown = false
                    onHideProgress()
                }
            } else {
                onProgressChanged(newProgress)
            }
        }

        private fun onShowProgress() {
            showProgress()
            if (mOnPageProgressCallback != null) {
                mOnPageProgressCallback.onShowProgress()
            }
        }

        private fun onProgressChanged(progress: Int) {
            setProgress(progress)
            if (mOnPageProgressCallback != null) {
                mOnPageProgressCallback.onProgressChanged(progress)
            }
        }

        private fun onHideProgress() {
            hideProgress()
            if (mOnPageProgressCallback != null) {
                mOnPageProgressCallback.onHideProgress()
            }
        }

        private fun setProgress(progress: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mProgressBar.setProgress(progress, true)
            } else {
                mProgressBar.setProgress(progress)
            }
        }

        private fun showProgress() {
            mProgressBar.animate().alpha(1f).setDuration(200).setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    mProgressBar.setVisibility(View.VISIBLE)
                    setProgress(0)
                }

                override fun onAnimationEnd(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            }).start()
        }

        private fun hideProgress() {
            mProgressBar.animate().alpha(0f).setDuration(200).setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    setProgress(100)
                    mProgressBar.setVisibility(View.GONE)
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            }).start()
        }

        private var mCustomViewLayer: DecorLayer? = null
        private var mCustomViewCallback: IX5WebChromeClient.CustomViewCallback? = null
        private var mOldActivityOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        override fun onShowCustomView(view: View, customViewCallback: IX5WebChromeClient.CustomViewCallback) {
            if (mCustomViewLayer != null) {
                mCustomViewLayer!!.dismiss()
                mCustomViewLayer = null
            }
            if (mCustomViewCallback != null) {
                mCustomViewCallback!!.onCustomViewHidden()
                mCustomViewCallback = null
            }
            mCustomViewCallback = customViewCallback
            mCustomViewLayer = DecorLayer(mActivity)
            mCustomViewLayer!!.level(Int.MAX_VALUE)
            mCustomViewLayer!!.animator(object : AnimatorCreator {
                override fun createInAnimator(target: View): Animator? {
                    return null
                }

                override fun createOutAnimator(target: View): Animator? {
                    return null
                }
            })
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            mCustomViewLayer!!.child(view)
            mCustomViewLayer!!.show()
            mOldActivityOrientation = mActivity.getRequestedOrientation()
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        override fun onHideCustomView() {
            if (mCustomViewLayer != null) {
                mCustomViewLayer!!.dismiss()
                mCustomViewLayer = null
            }
            if (mCustomViewCallback != null) {
                mCustomViewCallback!!.onCustomViewHidden()
                mCustomViewCallback = null
            }
            mActivity.setRequestedOrientation(mOldActivityOrientation)
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    inner class WanWebViewClient : WebViewClient() {
        private fun shouldInterceptRequest(
            reqUri: Uri, reqHeaders: Map<String, String>?, reqMethod: String?
        ): WebResourceResponse? {
            val url = reqUri.toString()
            WebHolder.syncCookiesForWanAndroid(url)
            return if (mInterceptUrlInterceptor == null) null else mInterceptUrlInterceptor.onInterceptUrl(
                reqUri, reqHeaders, reqMethod
            )
        }

        private fun shouldOverrideUrlLoading(view: WebView, uri: Uri): Boolean {
            FFLog.i(WebHolder.TAG, "shouldOverrideUrlLoading=$uri")
            val url = view.url
            val originalUrl = view.originalUrl
            val hit = view.hitTestResult
            if (hit.type == WebView.HitTestResult.UNKNOWN_TYPE || TextUtils.isEmpty(hit.extra)) {
                FFLog.i(WebHolder.TAG, "重定向:url=$url")
                FFLog.i(WebHolder.TAG, "重定向:originalUrl=$originalUrl")
                if (!allowRedirect) {
                    if (!TextUtils.isEmpty(originalUrl) && (originalUrl.startsWith("http://") || originalUrl.startsWith(
                            "https://"
                        ))
                    ) {
                        return true
                    }
                }
            }
            val scheme = uri.scheme
            if (!(TextUtils.equals(scheme, "http") || TextUtils.equals(scheme, "https"))) {
                if (allowOpenOtherApp) {
                    try {
                        val context = view.context
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return true
            }
            if (mOverrideUrlInterceptor != null) {
                if (mOverrideUrlInterceptor.onOverrideUrl(uri.toString())) {
                    return true
                }
            }
            when (HostInterceptUtils.TYPE_ONLY_WHITE) {
                HostInterceptUtils.TYPE_ONLY_WHITE -> if (!HostInterceptUtils.isWhiteHost(uri.host)) {
                    return true
                }
                HostInterceptUtils.TYPE_INTERCEPT_BLACK -> if (HostInterceptUtils.isBlackHost(uri.host)) {
                    return true
                }
                HostInterceptUtils.TYPE_NOTHING -> {
                }
                else -> {
                }
            }
            return false
        }

        override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse {
            val reqUri = Uri.parse(url)
            val response = shouldInterceptRequest(reqUri, null, null)
            return response ?: super.shouldInterceptRequest(view, url)
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse {
            val reqUri = request.url ?: return super.shouldInterceptRequest(view, request)
            val reqHeaders = request.requestHeaders
            val reqMethod = request.method
            val response = shouldInterceptRequest(reqUri, reqHeaders, reqMethod)
            return response ?: super.shouldInterceptRequest(view, request)
        }

        override fun shouldInterceptRequest(
            view: WebView, request: WebResourceRequest, bundle: Bundle
        ): WebResourceResponse {
            val reqUri = request.url ?: return super.shouldInterceptRequest(view, request, bundle)
            val reqHeaders = request.requestHeaders
            val reqMethod = request.method
            val response = shouldInterceptRequest(reqUri, reqHeaders, reqMethod)
            return response ?: super.shouldInterceptRequest(view, request, bundle)
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return shouldOverrideUrlLoading(view, Uri.parse(url))
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return shouldOverrideUrlLoading(view, request.url)
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            jsInjector.onPageStarted()
            super.onPageStarted(view, url, favicon)
            if (mOnPageTitleCallback != null) {
                mOnPageTitleCallback.onReceivedTitle(getUrl())
            }
            if (mOnPageLoadCallback != null) {
                mOnPageLoadCallback.onPageStarted()
            }
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            if (mOnPageTitleCallback != null) {
                mOnPageTitleCallback.onReceivedTitle(getTitle())
            }
            if (mOnPageLoadCallback != null) {
                mOnPageLoadCallback.onPageFinished()
            }
        }

        override fun doUpdateVisitedHistory(view: WebView, url: String, isReload: Boolean) {
            super.doUpdateVisitedHistory(view, url, isReload)
            if (mOnHistoryUpdateCallback != null) {
                mOnHistoryUpdateCallback.onHistoryUpdate(isReload)
            }
        }
    }



}

interface OnPageScrollEndListener {
    fun onPageScrollEnd()
}

interface OnPageTitleCallback {
    fun onReceivedTitle(title: String)
}

interface OnPageLoadCallback {
    fun onPageStarted()
    fun onPageFinished()
}

interface OnPageProgressCallback {
    fun onShowProgress()
    fun onProgressChanged(progress: Int)
    fun onHideProgress()
}

interface OnHistoryUpdateCallback {
    fun onHistoryUpdate(isReload: Boolean)
}

interface OverrideUrlInterceptor {
    fun onOverrideUrl(url: String?): Boolean
}

interface InterceptUrlInterceptor {
    fun onInterceptUrl(
        reqUri: Uri, reqHeaders: Map<String, String>?, reqMethod: String?
    ): WebResourceResponse?
}