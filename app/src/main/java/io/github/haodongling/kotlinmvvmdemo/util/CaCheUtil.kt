package io.github.haodongling.kotlinmvvmdemo.util

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import io.github.haodongling.lib.common.model.bean.User

/**
 * Author: tangyuan
 * Time : 2021/11/10
 * Description:
 */
object CaCheUtil {
    const val TABLE_WANANDROID="wan_android"
    const val TABLE_CACHE="cache"
    const val KEY_USER="user"
    const val KEY_HISTORY="history"

    fun getUser():User?{
        val kv= MMKV.mmkvWithID(TABLE_WANANDROID)
        val userStr=kv.decodeString(KEY_USER)
        return if (TextUtils.isEmpty(userStr)) {
            null
        } else {
            Gson().fromJson(userStr, User::class.java)
        }

    }

    /**
     * 获取搜索历史缓存数据
     */
    fun getSearchHistoryData(): ArrayList<String> {
        val kv = MMKV.mmkvWithID(TABLE_CACHE)
        val searchCacheStr =  kv.decodeString(KEY_HISTORY)
        if (!TextUtils.isEmpty(searchCacheStr)) {
            return Gson().fromJson(searchCacheStr
                , object : TypeToken<ArrayList<String>>() {}.type)
        }
        return arrayListOf()
    }
    fun setSearchHistoryData(searchResponseStr: String) {
        val kv = MMKV.mmkvWithID(TABLE_CACHE)
        kv.encode(KEY_HISTORY,searchResponseStr)
    }

}