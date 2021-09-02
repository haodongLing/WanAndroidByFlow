package io.github.haodongling.kotlinmvvmdemo.ui.home

import android.text.Html
import android.text.TextUtils
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import io.github.haodongling.kotlinmvvmdemo.R
import com.makeramen.roundedimageview.RoundedImageView
import io.github.haodongling.lib.utils.StringUtils

/**
 * Author: tangyuan
 * Time : 2021/8/20
 * Description:
 */
class HomeAdapter(layoutResId: Int) :
    BaseQuickAdapter<io.github.haodongling.lib.common.model.bean.Article, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: io.github.haodongling.lib.common.model.bean.Article) {
        helper.setGone(R.id.tv_top, item.top)
        helper.setGone(R.id.tv_new, item.fresh)
        helper.setText(R.id.tv_author, item.author)
        helper.setText(R.id.tv_time, item.niceDate)
        if (item.tags.isNotEmpty()&&item.tags.size>0){
            helper.setText(R.id.tv_tag,item.tags.get(0).name)
            helper.setGone(R.id.tv_tag,false)
        }else{
            helper.setGone(R.id.tv_tag,true)
        }
        if (item.envelopePic.isNullOrBlank()) {
            helper.setGone(R.id.iv_img, true)
        } else {
            helper.setGone(R.id.iv_img, false)
            val ivImg = helper.getView<RoundedImageView>(R.id.iv_img)
            Glide.with(context).load(item.envelopePic).into(ivImg)
        }
        val tvTitle = helper.getView<TextView>(R.id.tv_title)
        tvTitle.setText(Html.fromHtml(item.title))
        if (item.desc.isNullOrEmpty()) {
            helper.setGone(R.id.tv_desc, true)
            tvTitle.setSingleLine(false)
        } else {
            helper.setGone(R.id.tv_desc, false)
            tvTitle.setSingleLine(true)
            var desc = Html.fromHtml(item.desc).toString()
            desc = StringUtils.removeAllBank(desc, 2)
            helper.setText(R.id.tv_desc, desc)
        }
        val tv_chapter_name = helper.getView<TextView>(R.id.tv_chapter_name)
        tv_chapter_name.setText(formatChapterName(arrayOf(item.superChapterName, item.chapterName)))
        val cv_collect = helper.getView<io.github.haodongling.kotlinmvvmdemo.widget.CollectView>(R.id.cv_collect)
        cv_collect.setChecked(item.collect, false)


    }

    fun formatChapterName(names: Array<String>): String {
        val format = StringBuilder()
        for (name in names) {
            if (!TextUtils.isEmpty(name)) {
                if (format.length > 0) {
                    format.append("·")
                }
                format.append(name)
            }
        }
        return format.toString()
    }

}