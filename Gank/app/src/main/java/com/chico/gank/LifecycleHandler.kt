package com.chico.gank

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import java.util.*

/**
 * @ClassName: LifecycleHandler
 * @Description:
 * @Author: Chico
 * @Date: 2020/9/30 14:15
 */
open class LifecycleHandler : ActivityLifecycleCallbacks {

    private var activityCount = 0

    private val activitys = ArrayList<Activity>()

    private var topActivity: Activity? = null

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
        activityCount++
    }

    override fun onActivityDestroyed(activity: Activity) {
        activitys.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
        activityCount--
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        topActivity = activity
        activitys.add(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        topActivity = activity
    }

    /*判断是否在前台*/
    fun isForeground() = activityCount > 0
}