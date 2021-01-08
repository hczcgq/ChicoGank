package com.chico.gank.ui

import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.chico.gank.R
import com.chico.gank.base.BaseViewModelFragment
import com.chico.gank.http.GankViewModel
import com.chico.gank.model.TabItem
import com.chico.gank.ui.fragment.ArticleFragment
import com.chico.gank.ui.fragment.GanHuoFragment
import com.chico.gank.ui.fragment.GirlFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.main_tabs_widght.view.*

/**
 * @Author: Chico
 * @Date: 2020/12/28
 * @Description:
 */

const val TAB_GAN_HUO = 0
const val TAB_ARTICLE = 1
const val TAB_GIRL = 2

class MainFragment : BaseViewModelFragment<GankViewModel>() {

    private var ganHuoFragment: GanHuoFragment? = null
    private var articleFragment: ArticleFragment? = null
    private var girlFragment: GirlFragment? = null

    val tabs = arrayListOf<TabItem>()
    var tabIndex: Int = 0


    override fun getIntentData() {

    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_main
    }

    override fun initFragment() {
        super.initFragment()
        setToolbar(false)
        initTab()
    }

    private fun initTab() {
        val tabTextColor =
            ContextCompat.getColorStateList(requireContext(), R.color.nav_tab_text_color)
        tabs.add(
            TabItem(
                getString(R.string.tag_ganhuo),
                tabTextColor!!,
                R.drawable.tab_live_h,
                R.drawable.tab_live_n,
                TAB_GAN_HUO
            )
        )
        tabs.add(
            TabItem(
                getString(R.string.tag_article),
                tabTextColor,
                R.drawable.tab_live_h,
                R.drawable.tab_live_n,
                TAB_ARTICLE
            )
        )
        tabs.add(
            TabItem(
                getString(R.string.tag_girl),
                tabTextColor,
                R.drawable.tab_info_h,
                R.drawable.tab_info_n,
                TAB_GIRL
            )
        )
        for (index in tabs.indices) {
            val layout = layoutInflater.inflate(R.layout.main_tabs_widght, null, false)
            val item: TabItem = tabs[index]
            when (index) {
                0 -> layout.image.setImageResource(item.selectIcon)
                else -> layout.image.setImageResource(item.unSelectIcon)
            }
            layout.title.setTextColor(item.titleColor)
            layout.title.text = item.title
            val tabAt = tab_layout.newTab()
            tabAt.customView = layout
            tab_layout.addTab(tabAt)
        }
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                resetBottomTab()
                tabIndex = tab!!.position
                val item: TabItem = tabs[tabIndex]
                tab.customView?.image?.setImageResource(item.selectIcon)
                tab.customView?.title?.text = item.title
                showFragment(item.index)
            }
        })
        //默认页面
        tab_layout.getTabAt(0)!!.select()
        showFragment(TAB_GAN_HUO)
    }

    fun resetBottomTab() {
        val count = tab_layout.tabCount
        for (i in 0..count) {
            val customView = tab_layout.getTabAt(i)?.customView ?: return
            when (i) {
                TAB_GAN_HUO -> {
                    customView.title.text = getString(R.string.tag_ganhuo)
                    customView.image.setImageResource(R.drawable.tab_live_n)
                }
                TAB_ARTICLE -> {
                    customView.title.text = getString(R.string.tag_article)
                    customView.image.setImageResource(R.drawable.tab_info_n)
                }
                TAB_GIRL -> {
                    customView.title.text = getString(R.string.tag_girl)
                    customView.image.setImageResource(R.drawable.tab_info_n)
                }
            }
        }
    }


    /*显示fragment*/
    private fun showFragment(index: Int) {
        val ft = childFragmentManager.beginTransaction()
        hideFragment(ft)
        when (index) {
            TAB_GAN_HUO -> if (ganHuoFragment == null) {
                ganHuoFragment = GanHuoFragment()
                ft.add(R.id.fragment_content, ganHuoFragment!!, GanHuoFragment::class.java.name)
            } else {
                ft.show(ganHuoFragment!!)
            }
            TAB_ARTICLE -> if (articleFragment == null) {
                articleFragment = ArticleFragment()
                ft.add(R.id.fragment_content, articleFragment!!, ArticleFragment::class.java.name)
            } else {
                ft.show(articleFragment!!)
            }
            TAB_GIRL -> if (girlFragment == null) {
                girlFragment = GirlFragment()
                ft.add(R.id.fragment_content, girlFragment!!, GirlFragment::class.java.name)
            } else {
                ft.show(girlFragment!!)
            }
        }
        ft.commitAllowingStateLoss()
    }

    /*隐藏fragment*/
    private fun hideFragment(ft: FragmentTransaction) {
        if (ganHuoFragment != null) ft.hide(ganHuoFragment!!)

        if (articleFragment != null) ft.hide(articleFragment!!)

        if (girlFragment != null) ft.hide(girlFragment!!)
    }
}