package com.chico.gank.http

import com.chico.gank.model.ResultWeather
import com.chico.gank.model.Weather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: Chico
 * @Date: 2021/2/4
 * @Description:
 */
interface WeatherService {

    @GET("simpleWeather/query")
    fun queryWeather(
        @Query("city") city: String,
        @Query("key") key: String
    ): Observable<ResultWeather<Weather>>
}