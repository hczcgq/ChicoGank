package com.chico.gank.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.chico.gank.R
import com.chico.gank.base.BaseFragment
import com.chico.gank.base.BaseViewModelFragment
import com.chico.gank.http.WeatherViewModel
import com.chico.gank.ui.dialog.CleanCacheDialog
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * @Author: Chico
 * @Date: 2021/1/12
 * @Description:我的
 */
class MineFragment : BaseViewModelFragment<WeatherViewModel>() {

    private var city: String = "上海"

    override fun getIntentData() {

    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_mine
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        userVisibleHint = true
        super.onActivityCreated(savedInstanceState)
    }

    override fun initFragment() {
        super.initFragment()

        tv_about.setOnClickListener {
            start(AboutFragment())
        }

        tv_favorite.setOnClickListener {
            start(FavoriteFragment())
        }

        tv_clean.setOnClickListener {
            CleanCacheDialog().show(childFragmentManager, null)
        }

        viewmodel?.queryCity(city)

        viewmodel?.weather?.observe(this, Observer {
            tv_city.text = it.city
            tv_temperature.text = it.realtime?.temperature + "°"
            tv_info.text = it.realtime?.info
            tv_power.text = it.realtime?.power
            tv_humidity.text = it.realtime?.humidity
            tv_aqi.text = it.realtime?.aqi
            tv_power_text.text = it.realtime?.direct
        })
    }

}