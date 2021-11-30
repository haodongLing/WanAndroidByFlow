package io.github.haodongling.lib.utils.bitmap

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import io.github.haodongling.lib.utils.UriUtils
import io.github.haodongling.lib.utils.file.CacheUtils
import io.github.haodongling.lib.utils.global.AppGlobals
import java.io.*
import java.lang.Exception

/**
 * @author CuiZhen
 * @date 2019/11/3
 * GitHub: https://github.com/goweii
 */
object BitmapUtils {
    /**
     * @param bmp     获取的bitmap数据
     * @param picName 自定义的图片名
     */
    @Deprecated("")
    fun saveGallery2(bmp: Bitmap, picName: String?): File? {
        var outStream: FileOutputStream? = null
        return try {
            val gallery = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val file = File(gallery, picName)
            outStream = FileOutputStream(file.path)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val uri = UriUtils.getFileUri(file)
            intent.data = uri
           AppGlobals.getApplication().sendBroadcast(intent)
            file
        } catch (e: Exception) {
            e.stackTrace
            null
        } finally {
            try {
                outStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * @param bmp     获取的bitmap数据
     * @param picName 自定义的图片名
     */
    fun saveGallery(bmp: Bitmap, picName: String?): Boolean {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, picName)
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        } else {
            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val file = File(dir, picName)
            contentValues.put(MediaStore.MediaColumns.DATA, file.path)
        }
        val insertUri =
            AppGlobals.getApplication().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                ?: return false
        var outputStream: OutputStream? = null
        return try {
            outputStream = AppGlobals.getApplication().contentResolver.openOutputStream(insertUri)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            true
        } catch (e: FileNotFoundException) {
            false
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @JvmOverloads
    fun saveBitmapToCache(bm: Bitmap, name: String? = null): File? {
        var outStream: FileOutputStream? = null
        return try {
            val dir = CacheUtils.cacheDir
            val fileName: String?
            fileName = if (TextUtils.isEmpty(name)) {
                System.currentTimeMillis().toString() + ".jpg"
            } else {
                name
            }
            val f = File(dir, fileName)
            outStream = FileOutputStream(f)
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream.flush()
            outStream.close()
            f
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } finally {
            try {
                outStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun getImageContentUri(context: Context, path: String): Uri? {
        context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media._ID),
                MediaStore.Images.Media.DATA + "=? ",
                arrayOf(path),
                null
            ).use { cursor ->
                return if (cursor != null && cursor.moveToFirst()) {
                    val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                    val baseUri = Uri.parse("content://media/external/images/media")
                    Uri.withAppendedPath(baseUri, "" + id)
                } else {
                    if (File(path).exists()) {
                        val values = ContentValues()
                        values.put(MediaStore.Images.Media.DATA, path)
                        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                    } else {
                        null
                    }
                }
            }
    }

    // 通过uri加载图片
    fun getBitmapFromUri(context: Context, uri: Uri?): Bitmap? {
        try {
            val parcelFileDescriptor = context.contentResolver.openFileDescriptor(
                uri!!, "r"
            ) ?: return null
            val fileDescriptor = parcelFileDescriptor.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getBitmapFromPath(context: Context, path: String): Bitmap? {
        val uri = getImageContentUri(context, path) ?: return null
        return getBitmapFromUri(context, uri)
    }
}