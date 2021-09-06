package io.github.haodongling.kotlinmvvmdemo.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.lib.ui.MultiStateView
import io.github.haodongling.lib.utils.ext.gone
import io.github.haodongling.lib.utils.ext.visible
import io.github.haodongling.lib.utils.listener.OnClickListener2
import io.github.haodongling.lib.utils.listener.SimpleListener


/**
 * @author CuiZhen
 * @date 2019/5/25
 * GitHub: https://github.com/goweii
 */
class MultiStateUtils {
    companion object {

        @JvmStatic
        fun toLoading(view: MultiStateView) {
            view.viewState = MultiStateView.ViewState.LOADING
        }

        @JvmStatic
        @JvmOverloads
        fun toEmpty(view: MultiStateView, force: Boolean = false, icon: Int? = R.drawable.ic_empty, text: String? = "什么都木有~") {
            if (force || view.viewState != MultiStateView.ViewState.CONTENT) {
                view.viewState = MultiStateView.ViewState.EMPTY
            }
            view.getView(MultiStateView.ViewState.EMPTY)?.apply {
                val iv = findViewById<ImageView>(R.id.iv_empty)
                if (icon == null || icon <= 0) {
                    iv.setImageDrawable(ColorDrawable(Color.TRANSPARENT))
                    iv.gone()
                } else {
                    iv.setImageResource(icon)
                    iv.visible()
                }
                val tv = findViewById<TextView>(R.id.tv_empty)
                if (text.isNullOrEmpty()) {
                    tv.text = ""
                    tv.gone()
                } else {
                    tv.text = text
                    tv.visible()
                }
            }
        }

        @JvmStatic
        @JvmOverloads
        fun toError(view: MultiStateView, force: Boolean = false, icon: Int? = R.drawable.ic_error, text: String? = "怎么又出错~\n小场面，问题不大") {
            if (force || view.viewState != MultiStateView.ViewState.CONTENT) {
                view.viewState = MultiStateView.ViewState.ERROR
            }
            view.getView(MultiStateView.ViewState.ERROR)?.apply {
                val iv = findViewById<ImageView>(R.id.iv_error)
                if (icon == null || icon <= 0) {
                    iv.setImageDrawable(ColorDrawable(Color.TRANSPARENT))
                    iv.gone()
                } else {
                    iv.setImageResource(icon)
                    iv.visible()
                }
                val tv = findViewById<TextView>(R.id.tv_error)
                if (text.isNullOrEmpty()) {
                    tv.text = ""
                    tv.gone()
                } else {
                    tv.text = text
                    tv.visible()
                }
            }
        }

        @JvmStatic
        fun toContent(view: MultiStateView) {
            view.viewState = MultiStateView.ViewState.CONTENT
        }

        @JvmStatic
        fun setEmptyAndErrorClick(view: MultiStateView, listener: SimpleListener) {
            setEmptyClick(view, listener)
            setErrorClick(view, listener)
        }

        @JvmStatic
        fun setEmptyClick(view: MultiStateView, listener: SimpleListener) {
            val empty = view.getView(MultiStateView.ViewState.EMPTY)
            empty?.setOnClickListener(object : OnClickListener2() {
                override fun onClick2(v: View) {
                    listener.onResult()
                }
            })
        }

        @JvmStatic
        fun setErrorClick(view: MultiStateView, listener: SimpleListener) {
            val error = view.getView(MultiStateView.ViewState.ERROR)
            error?.setOnClickListener(object : OnClickListener2() {
                override fun onClick2(v: View) {
                    listener.onResult()
                }
            })
        }

    }
}