package com.chico.gank.ui.fragment

import com.chico.gank.R
import com.chico.gank.base.BaseFragment

/**
 * @Author: Chico
 * @Date: 2021/2/4
 * @Description:
 */
class AboutFragment : BaseFragment() {


    override fun getIntentData() {

    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_about
    }

    override fun initFragment() {

        setToolbar(true, "关于", R.drawable.back_white_icon)
    }

}