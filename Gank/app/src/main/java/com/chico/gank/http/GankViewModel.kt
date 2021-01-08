package com.chico.gank.http

import androidx.lifecycle.MutableLiveData
import com.chico.gank.base.BaseViewModel
import com.chico.gank.model.Article
import com.chico.gank.model.Banner

/**
 * @Author: Chico
 * @Date: 2020/12/28
 * @Description:
 */
class GankViewModel : BaseViewModel() {

    val api: ApiService = create(ApiService::class.java)

    val banner = MutableLiveData<List<Banner>>()

    val article = MutableLiveData<List<Article>>()


    fun getBanner() {
        val disposable = api.getBanner()
            .compose(ScheduleTransformer())
            .compose(ResultDisposable())
            .subscribeWith(object : ResultObserver<List<Banner>>() {
                override fun onResponse(res: List<Banner>) {
                    banner.value = res
                }

                override fun onError(code: Int, data: Any) {
                    banner.value = emptyList()
                }
            })
        addSubscribe(disposable)
    }

    fun getHot(page: Int) {
        val disposable = api.getHot(page)
            .compose(ScheduleTransformer())
            .compose(ResultDisposable())
            .subscribeWith(object : ResultObserver<List<Article>>() {
                override fun onResponse(res: List<Article>) {
                    article.value = res
                }

                override fun onError(code: Int, data: Any) {
                    article.value = emptyList()
                }
            })
        addSubscribe(disposable)
    }

    fun getArticle(category: String, type: String, page: Int) {
        val disposable = api.getArticle(category, type, page)
            .compose(ScheduleTransformer())
            .compose(ResultDisposable())
            .subscribeWith(object : ResultObserver<List<Article>>() {
                override fun onResponse(res: List<Article>) {
                    article.value = res
                }

                override fun onError(code: Int, data: Any) {
                    article.value = emptyList()
                }
            })
        addSubscribe(disposable)
    }
}