package io.github.haodongling.kotlinmvvmdemo.dialog

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.github.chrisbanes.photoview.PhotoView
import com.permissionx.guolindev.PermissionX
import com.ycbjie.webviewlib.utils.ToastUtils
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.lib.utils.bitmap.BitmapUtils
import per.goweii.anylayer.dialog.DialogLayer
import per.goweii.anylayer.widget.SwipeLayout
import kotlin.math.max

/**
 * @author CuiZhen
 * @date 2020/3/7
 */
class ImageMenuDialog(
        private val context: Context,
        private val bitmap: Bitmap,
        private val qrcode: String?
) : DialogLayer(context) {

    companion object {
        fun create(context: Context, iv: PhotoView, onCreate: (ImageMenuDialog) -> Unit) {
            try {
                val drawable = iv.drawable ?: return
                val bd = drawable as BitmapDrawable
                val bitmap = bd.bitmap ?: return
                val scale: Float = max(720F / bitmap.width.toFloat(), 720F / bitmap.height.toFloat())
                val newBitmap = if (scale < 1F) {
                    val w = (bitmap.width * scale).toInt()
                    val h = (bitmap.height * scale).toInt()
                    val bitmapScaled = Bitmap.createScaledBitmap(bitmap, w, h, false)
                    bitmapScaled
                } else {
                    bitmap
                }
//                CodeDecoder(ZXingMultiDecodeQRCodeProcessor()).decode(newBitmap, onSuccess = {
//                    val dialog = ImageMenuDialog(context, bitmap, it.first().text).apply {
//                        show()
//                    }
//                    onCreate.invoke(dialog)
//                }, onFailure = {
//                    val dialog = ImageMenuDialog(context, bitmap, null).apply {
//                        show()
//                    }
//                    onCreate.invoke(dialog)
//                })
            } catch (e: Exception) {
            }
        }
    }

    init {
        backgroundDimAmount(0.3F)
        contentView(R.layout.dialog_image_menu)
        swipeDismiss(SwipeLayout.Direction.BOTTOM)
        gravity(Gravity.BOTTOM)
        onClickToDismiss(R.id.dialog_image_menu_iv_dismiss)
    }

    private val tv_save by lazy { getView<TextView>(R.id.dialog_image_menu_tv_save)!! }
    private val tv_qrcode by lazy { getView<TextView>(R.id.dialog_image_menu_tv_qrcode)!! }

    @SuppressLint("ClickableViewAccessibility")
    override fun onAttach() {
        super.onAttach()
        if (qrcode.isNullOrEmpty()) {
            tv_qrcode.visibility= View.GONE
        } else {
            tv_qrcode.visibility=View.VISIBLE
            tv_qrcode.setOnClickListener {
                dismiss()
            }
        }
        tv_save.setOnClickListener {
            saveBitmap(bitmap)
            dismiss()
        }
    }

    private fun saveBitmap(bitmap: Bitmap) {
        PermissionX.init(context as FragmentActivity).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).request { allGranted, grantedList, deniedList ->
            if (BitmapUtils.saveGallery(bitmap, "wanandroid_article_image_${System.currentTimeMillis()}")) {
                ToastUtils.showToast("以保存到相册")
            } else {
                ToastUtils.showToast("保存失败")
            }
        }

    }
}