package com.haodong.lib.common.model.bean

import android.os.Parcelable
import com.haodong.lib.common.model.bean.Article
import kotlinx.android.parcel.Parcelize

data class Navigation(val articles: List<Article>,
                      val cid: Int,
                      val name: String)