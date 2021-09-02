package io.github.haodongling.lib.common.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SystemChild(val child: List<SystemChild>,
                       val courseId: Int,
                       val id: Int,
                       val name: String,
                       val order: Int,
                       val parentChapterId: Int,
                       val visible: Int):Parcelable