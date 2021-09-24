package io.github.haodongling.kotlinmvvmdemo.ui.knowledge

import android.text.Html
import android.view.LayoutInflater
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.lib.common.model.bean.SystemChild
import io.github.haodongling.lib.common.model.bean.SystemParent
import java.util.*

/**
 * Author: tangyuan
 * Time : 2021/9/6
 * Description:
 */
class KnowledgeAdapter(onItemClickListener: OnItemClickListener) : BaseQuickAdapter<SystemChild, BaseViewHolder>(layoutResId = R.layout.rv_item_knowledge) {
    val mFlexItemTextViewCaches: Queue<TextView> = LinkedList()
    val  mInflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }
    val mOnItemClickListener:OnItemClickListener;
    init {
        mOnItemClickListener=onItemClickListener
    }

    override fun convert(holder: BaseViewHolder, item: SystemChild) {
        holder.setText(R.id.tv_name, item.name)
        val fbl: FlexboxLayout = holder.getView(R.id.fbl)
        for (i in item.children.indices) {
            val systemChild = item.children[i];
            val child: TextView = createOrGetCacheFlexItemTextView(fbl)
            child.setText(Html.fromHtml(systemChild.name))
            child.setOnClickListener {
                mOnItemClickListener.onClick(item,i)
            }
            fbl.addView(child)
        }
        holder.itemView.setOnClickListener {
            mOnItemClickListener.onClick(item,0)
        }

    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        val fbl = holder.getView<FlexboxLayout>(R.id.fbl)
        for (i in 0 until fbl.childCount) {
            mFlexItemTextViewCaches.offer(fbl.getChildAt(i) as TextView)
        }
        fbl.removeAllViews()
    }

    private fun createOrGetCacheFlexItemTextView(fbl: FlexboxLayout): TextView {
        val tv = mFlexItemTextViewCaches.poll()
        return tv ?: createFlexItemTextView(fbl)
    }

    private fun createFlexItemTextView(fbl: FlexboxLayout): TextView {
        return (mInflater.inflate(R.layout.rv_item_knowledge_child, fbl, false) as TextView)
    }

    interface OnItemClickListener {
        fun onClick(bean:SystemChild, pos: Int)
    }
}