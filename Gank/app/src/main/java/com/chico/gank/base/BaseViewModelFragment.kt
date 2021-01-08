package com.chico.gank.base

import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * @Author: Chico
 * @Date: 2020/12/28
 * @Description:
 */
abstract class BaseViewModelFragment<T : BaseViewModel> : BaseFragment() {

    var viewmodel: T? = null

    protected override fun initFragment() {
        val cls =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        viewmodel = ViewModelProvider(this)[cls]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (viewmodel != null) {
            viewmodel!!.unSubscribe()
        }
    }
}