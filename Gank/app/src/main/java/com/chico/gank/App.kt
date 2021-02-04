package com.chico.gank

import android.app.Application
import androidx.room.Room
import com.chico.gank.model.CityInfo
import com.chico.gank.model.favorite.FavoriteDatabase
import pl.tajchert.nammu.Nammu.init

/**
 * @Author: Chico
 * @Date: 2020/12/24
 * @Description:
 */
class App : Application() {

    companion object {
        //判断是否被回收
        var systemRecycleStatus = -1

        private var instance: App? = null

        fun get(): App? {
            return instance
        }
    }


    private var lifecycleHandler: LifecycleHandler? = null

    fun getLifecycleHandler(): LifecycleHandler? {
        return lifecycleHandler
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        lifecycleHandler = LifecycleHandler()
        registerActivityLifecycleCallbacks(lifecycleHandler)
        //权限管理初始化
        init(this)
    }

    var cityInfo: CityInfo? = null
}