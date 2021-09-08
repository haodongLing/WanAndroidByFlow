package io.github.haodongling.lib.common.util

import android.content.res.AssetManager
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import io.github.haodongling.lib.common.model.bean.BottomBar
import io.github.haodongling.lib.common.model.bean.Destination
import io.github.haodongling.lib.utils.global.AppGlobals
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Author: tangyuan
 * Time : 2021/9/7
 * Description:
 */
object AppConfig {
    val sDestConfig: HashMap<String, Destination>
        get() {
            val content = parseFile("destination.json")
            return JSON.parseObject(content, object : TypeReference<HashMap<String, Destination>>() {})
        }
    val sBottombar: BottomBar
        get() {
            val content: String = parseFile("main_tabs_config.json")
            return JSON.parseObject(content, BottomBar::class.java)
        }

    fun parseFile(fileName: String): String {
        val asset: AssetManager = AppGlobals.getApplication().assets;
        val builder = StringBuilder()
        BufferedReader(InputStreamReader(asset.open(fileName))).use {
            var line: String
            while (true) {
                line = it.readLine() ?: break
                builder.append(line)
            }
        }
        return builder.toString()

    }


}