package io.github.haodongling.kotlinmvvmdemo.ui.knowledge

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayoutMediator
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.ActivityKnowledgeArticleBinding
import io.github.haodongling.lib.common.core.BaseVMActivity
import io.github.haodongling.lib.common.global.BizConst
import io.github.haodongling.lib.common.model.bean.SystemChild
import io.github.haodongling.lib.common.model.bean.SystemParent

/**
 * Author: tangyuan
 * Time : 2021/9/9
 * Description:
 */
@Route(path = BizConst.ACTIVITY_KNOWLEDGE_ARTICLE)
class KnowledgeArticleActivity : BaseVMActivity<ActivityKnowledgeArticleBinding>(), View.OnClickListener {
    @JvmField
    @Autowired
    var curPos: Int = 0

    @JvmField
    @Autowired(name = "systemChild")
    var systemChild: SystemChild? = null

    lateinit var mediator: TabLayoutMediator;

    override fun setVariable() {
    }

    override fun initView() {
        ARouter.getInstance().inject(this@KnowledgeArticleActivity)
        systemChild?.let { it ->
            mBinding.run {
                layoutStatus.ivLeft.setOnClickListener(this@KnowledgeArticleActivity)
                viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
                viewPager.adapter = object : FragmentStateAdapter(this@KnowledgeArticleActivity) {
                    override fun getItemCount(): Int {
                        return it.children.size
                    }

                    override fun createFragment(position: Int): Fragment {
                        return KnowledgeArticleFragment.getInstance(it.children[position])
                    }
                }
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = it.children.get(position).name
                }.attach()
                layoutStatus.tvTitle.text= it.name;
            }


        }

    }

    override fun initData() {
    }

    override fun startObserve() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_knowledge_article
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when(it.id){
                R.id.iv_left->{
                    onBackPressed()
                }
            }

        }
    }
}