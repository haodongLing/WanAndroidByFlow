package io.github.haodongling.kotlinmvvmdemo.ui.knowledge

import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.widget.CollectView
import io.github.haodongling.lib.common.model.bean.Article
import io.github.haodongling.lib.common.util.ImageLoader
import io.github.haodongling.lib.utils.StringUtils

/**
 * Author: tangyuan
 * Time : 2021/9/23
 * Description:
 */
class ArticleAdapter : BaseQuickAdapter<Article,BaseViewHolder>(R.layout.rv_item_article){
    override fun convert(holder: BaseViewHolder, item: Article) {
        val tv_top: TextView = holder.getView<TextView>(R.id.tv_top)
        val tv_new: TextView = holder.getView<TextView>(R.id.tv_new)
        val tv_author: TextView = holder.getView<TextView>(R.id.tv_author)
        val tv_tag: TextView = holder.getView<TextView>(R.id.tv_tag)
        val tv_time: TextView = holder.getView<TextView>(R.id.tv_time)
        val iv_img: ImageView = holder.getView<ImageView>(R.id.iv_img)
        val tv_title: TextView = holder.getView<TextView>(R.id.tv_title)
        val tv_desc: TextView = holder.getView<TextView>(R.id.tv_desc)
        val tv_chapter_name: TextView = holder.getView<TextView>(R.id.tv_chapter_name)
        val cv_collect: CollectView = holder.getView(R.id.cv_collect)
        if (item.top) {
            tv_top.visibility = View.VISIBLE
        } else {
            tv_top.visibility = View.GONE
        }
        if (item.fresh) {
            tv_new.visibility = View.VISIBLE
        } else {
            tv_new.visibility = View.GONE
        }
        tv_author.setText(item.author)
        if (item.tags != null && item.tags.size > 0) {
            tv_tag.setText(item.tags.get(0).getName())
            tv_tag.visibility = View.VISIBLE
//            tv_tag.setOnClickListener(object : OnClickListener2() {
//                fun onClick2(v: View) {
//                    KnowledgeArticleActivity.start(v.context, item.getTags().get(0))
//                }
//            })
        } else {
            tv_tag.visibility = View.GONE
        }
        tv_time.setText(item.niceDate)
        if (!TextUtils.isEmpty(item.envelopePic)) {
            ImageLoader.image(iv_img, item.envelopePic)
            iv_img.visibility = View.VISIBLE
        } else {
            iv_img.visibility = View.GONE
        }
        tv_title.text = Html.fromHtml(item.title)
        if (TextUtils.isEmpty(item.desc)) {
            tv_desc.visibility = View.GONE
            tv_title.isSingleLine = false
        } else {
            tv_desc.visibility = View.VISIBLE
            tv_title.isSingleLine = true
            var desc = Html.fromHtml(item.desc).toString()
            desc = StringUtils.removeAllBank(desc, 2)
            tv_desc.text = desc
        }
    }

}