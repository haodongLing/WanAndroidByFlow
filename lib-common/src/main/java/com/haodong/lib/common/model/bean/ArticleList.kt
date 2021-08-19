package com.haodong.lib.common.model.bean

import android.os.Parcelable
import com.haodong.lib.common.model.bean.Article
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class ArticleList( val offset: Int,
                        val size: Int,
                        val total: Int,
                        val pageCount: Int,
                        val curPage: Int,
                        val over: Boolean,
                        val datas: List<Article>)