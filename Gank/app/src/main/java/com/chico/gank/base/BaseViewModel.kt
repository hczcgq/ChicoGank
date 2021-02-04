package com.chico.gank.base

import com.chico.gank.App
import com.chico.gank.http.HttpClient
import com.chico.gank.util.ToastUtils
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

import java.util.*

/**
 * @ClassName: BaseViewModel
 * @Description:
 * @Author: Chico
 * @Date: 2019/8/26 16:03
 */
open class BaseViewModel : ObservableViewModel() {

    private var mCompositeDisposable: CompositeDisposable? = null

    protected fun addSubscribe(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable!!)
    }

    protected fun removeSubscribe(disposable: Disposable?) {
        if (mCompositeDisposable != null && disposable != null) {
            mCompositeDisposable!!.remove(disposable)
        }
    }

    fun unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.dispose()
        }
    }

    protected fun <T> create(cls: Class<T>?): T {
        return HttpClient().get()?.retrofit()?.create(cls)!!
    }

    protected fun <T> create(cls: Class<T>?, url: String): T {
        return HttpClient().get()?.retrofit(url)?.create(cls)!!
    }

    /*map转换成json*/
    protected fun toJson(map: HashMap<String, Any>?): String {
        return Gson().toJson(map)
    }

    /*list转换成json*/
    protected fun toJson(list: List<Int>): String {
        return Gson().toJson(list)
    }

    /*显示错误提示*/
    protected fun showToast(data: Any?) {
        if (data != null && data is String) {
            ToastUtils.showShortToast(App.get(), data)
        }
    }
}