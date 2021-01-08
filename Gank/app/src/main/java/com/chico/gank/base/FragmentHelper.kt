package com.chico.gank.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.chico.gank.util.ToastUtils

/**
 * @Author: Chico
 * @Date: 2020/12/25
 * @Description:
 */
object FragmentHelper {

    @JvmStatic
    fun start(context: Context?, baseFragment: BaseFragment?) {
        start(context, baseFragment, -1)
    }

    @JvmStatic
    fun start(context: Context?, cls: Class<*>?) {
        start(context, cls, null, -1)
    }

    @JvmStatic
    fun start(context: Context?, cls: Class<*>?, bundle: Bundle?) {
        start(context, cls, bundle, -1)
    }

    @JvmStatic
    fun start(context: Context?, baseFragment: BaseFragment?, requestCode: Int) {
        start(context, baseFragment, BaseActivity::class.java, null, requestCode)
    }

    @JvmStatic
    fun start(context: Context?, cls: Class<*>?, bundle: Bundle?, requestCode: Int) {
        start(context, null, cls, bundle, requestCode)
    }

    @JvmStatic
    fun start(
        context: Context?,
        baseFragment: BaseFragment?,
        cls: Class<*>?,
        bundle: Bundle?,
        requestCode: Int
    ) {
        var requestCode = requestCode
        val intent = Intent(context, cls)
        if (context === context?.applicationContext) {
            requestCode = -1
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (baseFragment != null) {
            intent.putExtra(BaseActivity.INTENT_FRAGMENT, baseFragment.javaClass.name)
            val bundle = baseFragment.arguments
            if (bundle != null) {
                intent.putExtras(bundle)
            }
        } else {
            if (bundle != null) {
                intent.putExtras(bundle)
            }
        }
        try {
            if (requestCode == -1) {
                context?.startActivity(intent)
            } else if (context is FragmentActivity) {
                context.startActivityForResult(intent, requestCode)
            }
        } catch (e: Exception) {
            if (context !== context?.applicationContext) {
                start(context?.applicationContext, baseFragment, cls, bundle, requestCode)
            } else {
                ToastUtils.showShortToast(context, e.message)
            }
        }
    }
}