package io.github.haodongling.lib.common.ext

import com.google.gson.Gson

/**
 * Author: tangyuan
 * Time : 2021/11/10
 * Description:
 */
fun Any?.toJson():String{
    return Gson().toJson(this)
}
