package com.chico.gank.http

import com.chico.gank.App
import com.chico.gank.http.exception.EmptyException
import com.chico.gank.http.exception.ResponseException
import com.chico.gank.util.ToastUtils
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Author: Chico
 * @Date: 2020/12/28
 * @Description:
 */
abstract class ResultObserver<T> : ObserverBridge<T>() {
    override fun onComplete() {

    }

    override fun onNext(value: T) {
        onResponse(value)
    }

    override fun onError(e: Throwable) {
        when (e) {
            is ResponseException -> {
                onError(e.code, if (e.data != null) e.data else e.message)
            }
            is EmptyException -> {
                onResponse(getNullInstance())
            }
            else -> {
                onError(0, "")
                if (e is UnknownHostException
                    || e is ConnectException
                    || e is SocketTimeoutException
                ) {
                    ToastUtils.showShortToast(App.get(), "网络请求出错啦")
                } else {
                    ToastUtils.showShortToast(App.get(), e.message)
                }
            }
        }
    }

    protected abstract fun onResponse(res: T)

    protected abstract fun onError(code: Int, data: Any)
}