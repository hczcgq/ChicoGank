package com.chico.gank.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * @Author: Chico
 * @Date: 2020/12/25
 * @Description:
 */
class BaseFragment : Fragment(), FragmentHelperView, ToolBarView, FragmentOnBackStackListener {

    //Fragment的View加载完毕的标记
    protected var isViewCreated = false

    //Fragment对用户可见的标记
    protected var isUIVisible = false
    override fun start(baseFragment: BaseFragment?) {
        TODO("Not yet implemented")
    }

    override fun start(cls: Class<*>?) {
        TODO("Not yet implemented")
    }

    override fun start(cls: Class<*>?, bundle: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun start(baseFragment: BaseFragment?, requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun start(cls: Class<*>?, bundle: Bundle?, requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun start(
        baseFragment: BaseFragment?,
        cls: Class<*>?,
        bundle: Bundle?,
        requestCode: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getToolBarTitle(): TextView {
        TODO("Not yet implemented")
    }

    override fun setToolbar(hasToolbar: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setToolbar(hasToolbar: Boolean, title: String?) {
        TODO("Not yet implemented")
    }

    override fun setToolbar(hasToolbar: Boolean, title: String?, backImg: Int) {
        TODO("Not yet implemented")
    }

    override fun setToolbar(
        hasToolbar: Boolean,
        title: String?,
        backImg: Int,
        backgroundColor: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun setToolbar(
        hasToolbar: Boolean,
        title: String?,
        backImg: Int,
        backgroundColor: Int,
        titleColor: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun onBackPressed(): Boolean {
        TODO("Not yet implemented")
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (arguments != null) {
//            getIntentData()
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return if (isBinding()) {
//            val binding: ViewDataBinding =
//                DataBindingUtil.inflate(inflater, getContentViewId(), container, false)
//            setBinding(binding)
//            setHasOptionsMenu(true)
//            binding.getRoot()
//        } else {
//            val rootView: View = inflater.inflate(getContentViewId(), null)
//            setViewLayout(rootView)
//            setHasOptionsMenu(true)
//            rootView
//        }
//    }
}