package io.github.haodongling.lib.webview

import java.io.Serializable

/**
 * Author: tangyuan
 * Time : 2021/9/10
 * Description:
 */
class RequestInfo :Serializable {
    var url:String?=null
    var headers:Map<String,String>?=null
    constructor(url:String,additionalHttpHeaders:Map<String,String>){
        this.url=url
        this.headers=headers
    }
}