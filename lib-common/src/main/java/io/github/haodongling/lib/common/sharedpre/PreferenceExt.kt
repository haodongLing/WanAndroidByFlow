package io.github.haodongling.lib.common.sharedpre

import android.content.Context
import io.github.haodongling.lib.utils.global.AppGlobals
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Author: tangyuan
 * Time : 2021/9/26
 * Description:
 */
class PreferenceExt <T>(val context:Context=AppGlobals.getApplication(),val name: String, val default:T, val prefName:String="wan_android") : ReadWriteProperty<Any?,T>{
    private val prefs by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(findProperName(property))
    }
    private fun findProperName(property: KProperty<*>) = if(name.isEmpty()) property.name else name

    private fun findPreference(key: String): T{
        return when(default){
            is Long -> prefs.getLong(key, default)
            is Int -> prefs.getInt(key, default)
            is Boolean -> prefs.getBoolean(key, default)
            is String -> prefs.getString(key, default)
            else -> throw IllegalArgumentException("Unsupported type.")
        } as T
    }


    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(findProperName(property), value)
    }

    private fun putPreference(key: String, value: T){
        with(prefs.edit()){
            when(value){
                is Long -> putLong(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException("Unsupported type.")
            }
        }.apply()
    }

}