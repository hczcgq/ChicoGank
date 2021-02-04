package com.chico.gank.http

import android.os.Build
import androidx.annotation.RequiresApi
import com.chico.gank.http.exception.EmptyException
import com.chico.gank.http.exception.ResponseException

import com.chico.gank.model.Result
import com.chico.gank.model.ResultWeather
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer


/**
 * @Author: Chico
 * @Date: 2020/12/28
 * @Description:
 */
class ResultWeatherDisposable<T> : ObservableTransformer<ResultWeather<T>, T> {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun apply(upstream: Observable<ResultWeather<T>>): ObservableSource<T> {
        return upstream.flatMap {
            if (it.error_code == 0) {
                if (it.result == null) {
                    throw EmptyException()
                } else {
                    return@flatMap Observable.just(it.result)
                }
            } else {
                throw ResponseException(it.error_code, it.reason, it.result)
            }
        }
    }
}