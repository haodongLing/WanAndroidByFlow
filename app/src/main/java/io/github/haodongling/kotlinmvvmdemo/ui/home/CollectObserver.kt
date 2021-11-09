package io.github.haodongling.kotlinmvvmdemo.ui.home

import androidx.lifecycle.Observer
import io.github.haodongling.kotlinmvvmdemo.model.event.CollectEvent

/**
 * Author: tangyuan
 * Time : 2021/11/9
 * Description:
 */
class CollectObserver :Observer<CollectEvent> {
    var  event :CollectEvent? =null
    override fun onChanged(t: CollectEvent?) {
        event=t
    }
    fun setCollectEvent(collectEvent : CollectEvent){
        event=collectEvent
    }
}