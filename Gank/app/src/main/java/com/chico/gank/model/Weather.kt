package com.chico.gank.model

import java.io.Serializable

/**
 * @Author: Chico
 * @Date: 2021/2/4
 * @Description:
 */
data class Weather(
    var city: String,
    var realtime: Realtime? = null,
    var future: List<Future>? = null
) : Serializable {


    data class Realtime(
        var temperature: String,
        var humidity: String,
        var info: String,
        var wid: String,
        var direct: String,
        var power: String,
        var aqi: String
    ) : Serializable

    data class Future(
        var date: String,
        var temperature: String,
        var weather: String,
        var direct: String,
        var wid: Wid
    ) : Serializable

    data class Wid(
        var day: String,
        var night: String
    ) : Serializable

}