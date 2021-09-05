package io.github.haodongling.kotlinmvvmdemo.ui.knowledge

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.FragmentKnowledgeBinding
import io.github.haodongling.kotlinmvvmdemo.databinding.FragmentKnowledgeNavBinding
import io.github.haodongling.lib.common.core.BaseVMFragment
import kotlinx.android.synthetic.main.fragment_knowledge_nav.*

/**
 * Author: tangyuan
 * Time : 2021/8/16
 * Description:
 */
class KnowledgeNavFragment : BaseVMFragment<FragmentKnowledgeNavBinding>(R.layout.fragment_knowledge_nav) {
    private val titleList = arrayOf("体系", "导航")
    private val fragmentList = arrayListOf<Fragment>()
    private val knowledgeFragment by lazy {
        KnowledgeFragment()
    }
    private val navFragment by lazy { NavFragment() }

    init {
        fragmentList.add(knowledgeFragment)
        fragmentList.add(navFragment)
    }

    override fun initView() {
        mBinding.let { it ->
            viewPager.offscreenPageLimit = 1
            viewPager.adapter = object : FragmentStateAdapter(this) {
                override fun createFragment(position: Int): Fragment {
                    return fragmentList[position]
                }

                override fun getItemCount(): Int {
                    return titleList.size
                }
            }


        }
        TabLayoutMediator(tab_layout,viewPager){tab,position->
            tab.text=titleList[position]

        }.attach()


    }

    override fun initData() {
    }

    override fun startObserve() {
    }

    override fun setVariable() {

    }
}