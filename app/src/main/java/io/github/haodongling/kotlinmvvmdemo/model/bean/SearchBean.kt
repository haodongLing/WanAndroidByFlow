package io.github.haodongling.kotlinmvvmdemo.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 搜索热词
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class SearchBean(var id: Int,
                      var link: String,
                      var name: String,
                      var order: Int,
                      var visible: Int) : Parcelable
