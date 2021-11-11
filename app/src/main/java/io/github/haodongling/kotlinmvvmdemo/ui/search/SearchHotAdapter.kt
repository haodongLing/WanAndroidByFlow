package io.github.haodongling.kotlinmvvmdemo.ui.search

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.model.bean.SearchBean
import io.github.haodongling.kotlinmvvmdemo.util.ColorUtil


class SearchHotAdapter(data: ArrayList<SearchBean>) :
    BaseQuickAdapter<SearchBean, BaseViewHolder>(R.layout.item_flow, data) {
    init {
        this.animationEnable = true
        this.setAnimationWithDefault(AnimationType.AlphaIn)
    }

    override fun convert(holder: BaseViewHolder, item: SearchBean) {
        holder.setText(R.id.flow_tag, item.name)
        holder.setTextColor(R.id.flow_tag, ColorUtil.randomColor())
    }

}