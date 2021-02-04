package com.chico.gank.http


import androidx.lifecycle.MutableLiveData
import com.chico.gank.base.BaseViewModel
import com.chico.gank.model.Weather

/**
 * @Author: Chico
 * @Date: 2021/2/4
 * @Description:
 */
class WeatherViewModel : BaseViewModel() {

    val api: WeatherService = create(WeatherService::class.java,"http://apis.juhe.cn/")
    val key = "6e96e6d68fc8672e4bd18eb9423dd282"

    val weather = MutableLiveData<Weather>()

    fun queryCity(city: String) {
        val disposable = api.queryWeather(city, key)
            .compose(ScheduleTransformer())
            .compose(ResultWeatherDisposable())
            .subscribeWith(object : ResultObserver<Weather>() {
                override fun onResponse(res: Weather) {
                    weather.value = res
                }

                override fun onError(code: Int, data: Any) {
                    weather.value = null
                }
            })
        addSubscribe(disposable)
    }
}