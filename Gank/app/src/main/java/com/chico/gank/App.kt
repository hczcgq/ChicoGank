package com.chico.gank

import android.app.Application

/**
 * @Author: Chico
 * @Date: 2020/12/24
 * @Description:
 */
class App : Application() {

    companion object {
        //判断是否被回收
        var systemRecycleStatus = -1
    }
}