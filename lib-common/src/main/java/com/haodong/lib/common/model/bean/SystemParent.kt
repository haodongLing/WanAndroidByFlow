package com.haodong.lib.common.model.bean

import android.os.Parcelable
import com.haodong.lib.common.model.bean.SystemChild
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class SystemParent(val children: List<SystemChild>,
                        val courseId: Int,
                        val id: Int,
                        val name: String,
                        val order: Int,
                        val parentChapterId: Int,
                        val visible: Int,
                        val userControlSetTop: Boolean) : Parcelable