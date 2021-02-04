package com.chico.gank.model

import java.io.Serializable

/**
 * @Author: Chico
 * @Date: 2021/2/1
 * @Description:
 */
data class CityInfo(
    var city_name:String,
    var province_name:String,
    var longitude:Double,
    var latitude:Double
):Serializable