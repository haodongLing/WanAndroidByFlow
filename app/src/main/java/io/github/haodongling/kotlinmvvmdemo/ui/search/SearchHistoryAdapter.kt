package io.github.haodongling.kotlinmvvmdemo.ui.search

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import io.github.haodongling.kotlinmvvmdemo.R

class SearchHistoryAdapter(data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_item_history, data) {

    init {
        this.animationEnable = true
        this.setAnimationWithDefault(AnimationType.AlphaIn)
    }

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.item_history_text, item)
    }

}