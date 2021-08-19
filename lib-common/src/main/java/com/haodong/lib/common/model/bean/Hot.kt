package com.haodong.lib.common.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hot(val id: Int,
               val link: String,
               val name: String,
               val order: Int,
               val visible: Int,
               val icon: String):Parcelable