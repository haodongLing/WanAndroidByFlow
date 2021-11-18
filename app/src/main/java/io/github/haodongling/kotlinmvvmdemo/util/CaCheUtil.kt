package io.github.haodongling.kotlinmvvmdemo.util

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import io.github.haodongling.lib.common.model.bean.User

/**
 * Author: tangyuan
 * Time : 2021/11/10
 * Description: 缓存数据管理类
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
     * 设置账户信息
     */
    fun setUser(userResponse: User?) {
        val kv = MMKV.mmkvWithID("app")
        if (userResponse == null) {
            kv.encode("user", "")
            setIsLogin(false)
        } else {
            kv.encode("user", Gson().toJson(userResponse))
            setIsLogin(true)
        }

    }

    /**
     * 是否已经登录
     */
    fun isLogin(): Boolean {
        val kv = MMKV.mmkvWithID("app")
        return kv.decodeBool("login", false)
    }

    /**
     * 设置是否已经登录
     */
    fun setIsLogin(isLogin: Boolean) {
        val kv = MMKV.mmkvWithID("app")
        kv.encode("login", isLogin)
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