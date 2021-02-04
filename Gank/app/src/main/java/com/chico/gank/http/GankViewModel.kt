package com.chico.gank.http

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.amap.api.location.AMapLocation
import com.chico.gank.App
import com.chico.gank.base.BaseViewModel
import com.chico.gank.model.Article
import com.chico.gank.model.ArticleDetail
import com.chico.gank.model.Banner
import com.chico.gank.model.CityInfo
import com.chico.gank.model.favorite.Favorite
import com.chico.gank.model.favorite.FavoriteDatabase
import com.chico.gank.util.LocationUtils
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * @Author: Chico
 * @Date: 2020/12/28
 * @Description:
 */
class GankViewModel : BaseViewModel() {

    val api: ApiService = create(ApiService::class.java)

    val banner = MutableLiveData<List<Banner>>()

    val article = MutableLiveData<List<Article>>()

    val articleDetail = MutableLiveData<ArticleDetail>()

    val favoriteStatus = MutableLiveData<Boolean>()

    val favoriteDeal = MutableLiveData<Boolean>()

    val favorite = MutableLiveData<List<Favorite>>()


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

    fun getArticleDetail(post_id: String) {
        val disposable = api.getArticleDetail(post_id)
            .compose(ScheduleTransformer())
            .compose(ResultDisposable())
            .subscribeWith(object : ResultObserver<ArticleDetail>() {
                override fun onResponse(res: ArticleDetail) {
                    articleDetail.value = res
                }

                override fun onError(code: Int, data: Any) {
                    articleDetail.value = null
                }
            })
        addSubscribe(disposable)
    }

    fun search(key: String, category: String) {
        val disposable = api.search(key, category)
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

    /*获取定位信息*/
    fun requestLocationInfo() {
        LocationUtils.getLocation(App.get(),
            object : LocationUtils.OnLocationListener {
                override fun onSuccess(aMapLocation: AMapLocation) {
                    //获取经纬度
                    val longitude = aMapLocation.longitude
                    val latitude = aMapLocation.latitude
                    //获取城市
                    val cityName = aMapLocation.city
                    val provinceName = aMapLocation.province
                    App.get()?.cityInfo = CityInfo(cityName, provinceName, longitude, latitude)
                }

                override fun onFail(errorCode: Int, errorMsg: String?) {

                }

                override fun onComplete() {}
            })
    }

    fun checkIsFavorite(context: Context, id: String) {
        val disposable = Observable.create<Boolean> {
            val dao = FavoriteDatabase.getDatabase(context).favoriteDao()
            val data = dao.loadAllById(id)
            if (data != null && data.isNotEmpty()) {
                it.onNext(true)
            } else {
                it.onNext(false)
            }
        }
            .compose(ScheduleTransformer())
            .subscribeWith(object : ResultObserver<Boolean>() {
                override fun onResponse(res: Boolean) {
                    favoriteStatus.value = res
                }

                override fun onError(code: Int, data: Any) {
                    favoriteStatus.value = null
                }
            })
        addSubscribe(disposable)
    }

    fun insertFavorite(context: Context, favorite: Favorite) {
        val disposable = Observable.create<Boolean> {
            val dao = FavoriteDatabase.getDatabase(context).favoriteDao()
            val data = dao.loadAllById(favorite.id)
            if (data != null && data.isNotEmpty()) {
                dao.deleteFavorite(favorite)
                it.onNext(false)
            } else {
                dao.insertAll(favorite)
                it.onNext(true)
            }
        }
            .compose(ScheduleTransformer())
            .subscribeWith(object : ResultObserver<Boolean>() {
                override fun onResponse(res: Boolean) {
                    favoriteDeal.value = res
                }

                override fun onError(code: Int, data: Any) {
                    favoriteDeal.value = null
                }
            })
        addSubscribe(disposable)
    }

    fun getFavorite(context: Context) {
        val disposable = Observable.create<List<Favorite>> {
            val dao = FavoriteDatabase.getDatabase(context).favoriteDao()
            val data = dao.getAll()
            it.onNext(data)
        }
            .compose(ScheduleTransformer())
            .subscribeWith(object : ResultObserver<List<Favorite>>() {
                override fun onResponse(res: List<Favorite>) {
                    favorite.value = res
                }

                override fun onError(code: Int, data: Any) {
                    favorite.value = emptyList()
                }
            })
        addSubscribe(disposable)
    }
}