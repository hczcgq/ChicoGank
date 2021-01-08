package com.chico.gank.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chico.gank.R
import com.chico.gank.base.BaseFragment
import com.chico.gank.base.TabStatePageAdapter
import com.chico.gank.util.*
import kotlinx.android.synthetic.main.base_toolbar.*
import kotlinx.android.synthetic.main.fragment_ganhuo.*

/**
 * @Author: Chico
 * @Date: 2021/1/7
 * @Description: 干货
 */
class GanHuoFragment : BaseFragment() {

    private var titles: Array<String>? = null
    private var fragments = arrayListOf<Fragment>()

    override fun getIntentData() {

    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_ganhuo
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        userVisibleHint = true
        super.onActivityCreated(savedInstanceState)
    }

    override fun initFragment() {
        toolbar_title.text = "干货"
        titles = resources.getStringArray(R.array.tab_ganhuo)

        fragments.add(CategoryFragment.instance(CATEGORY_GAN_HUO, GAN_HUO_ANDROID))
        fragments.add(CategoryFragment.instance(CATEGORY_GAN_HUO, GAN_HUO_IOS))
        fragments.add(CategoryFragment.instance(CATEGORY_GAN_HUO, GAN_HUO_FLUTTER))
        fragments.add(CategoryFragment.instance(CATEGORY_GAN_HUO, GAN_HUO_FRONT_END))
        fragments.add(CategoryFragment.instance(CATEGORY_GAN_HUO, GAN_HUO_APP))

        val adapter = TabStatePageAdapter(childFragmentManager, fragments, titles)
        view_page.adapter = adapter
        view_page.offscreenPageLimit = adapter.count
        view_page.clearAnimation()
        tab_layout.setViewPager(view_page, titles)
    }
}