package com.chico.gank.util

import android.text.TextUtils
import android.util.Log
import com.chico.gank.BuildConfig

/**
 * @ClassName: Logger
 * @Description:Log日志管理
 * @Author: Chico
 * @Date: 2020/3/20 10:28
 */
object Logger {
    private const val TAG = "Leisu"

    @JvmStatic
    fun v(str: String?) {
        if (BuildConfig.DEBUG && str != null && !TextUtils.isEmpty(str)) {
            Log.v(TAG, str)
        }
    }

    @JvmStatic
    fun v(tag: String, str: String?) {
        if (BuildConfig.DEBUG && str != null && !TextUtils.isEmpty(str)) {
            Log.v(tag, str)
        }
    }


    @JvmStatic
    fun d(str: String?) {
        if (BuildConfig.DEBUG && str != null && !TextUtils.isEmpty(str)) {
            Log.d(TAG, str)
        }
    }

    @JvmStatic
    fun d(tag: String, str: String?) {
        if (BuildConfig.DEBUG && str != null && !TextUtils.isEmpty(str)) {
            Log.d(tag, str)
        }
    }


    @JvmStatic
    fun i(str: String?) {
        if (BuildConfig.DEBUG && str != null && !TextUtils.isEmpty(str)) {
            Log.i(TAG, str)
        }
    }

    @JvmStatic
    fun i(tag: String, str: String?) {
        if (BuildConfig.DEBUG && str != null && !TextUtils.isEmpty(str)) {
            Log.i(tag, str)
        }
    }

    @JvmStatic
    fun w(str: String?) {
        if (BuildConfig.DEBUG && str != null && !TextUtils.isEmpty(str)) {
            Log.w(TAG, str)
        }
    }

    @JvmStatic
    fun w(tag: String, str: String?) {
        if (BuildConfig.DEBUG && str != null && !TextUtils.isEmpty(str)) {
            Log.w(tag, str)
        }
    }

    @JvmStatic
    fun e(str: String?) {
        if (BuildConfig.DEBUG && str != null && !TextUtils.isEmpty(str)) {
            Log.e(TAG, str)
        }
    }

    @JvmStatic
    fun e(tag: String, str: String?) {
        if (BuildConfig.DEBUG && str != null && !TextUtils.isEmpty(str)) {
            Log.e(tag, str)
        }
    }
}