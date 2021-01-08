package com.chico.gank.http

import android.os.Build
import androidx.annotation.RequiresApi
import com.chico.gank.http.exception.EmptyException
import com.chico.gank.http.exception.ResponseException

import com.chico.gank.model.Result
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer


/**
 * @Author: Chico
 * @Date: 2020/12/28
 * @Description:
 */
class ResultDisposable<T> : ObservableTransformer<Result<T>, T> {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun apply(upstream: Observable<Result<T>>): ObservableSource<T> {
        return upstream.flatMap {
            if (it.code == 0) {
                if (it.data == null) {
                    throw EmptyException()
                } else {
                    return@flatMap Observable.just(it.data)
                }
            } else {
                throw ResponseException(it.code, it.msg, it.data)
            }
        }
    }
}