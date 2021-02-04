package com.chico.gank.base

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.chico.gank.R
import com.chico.gank.ui.dialog.LoadingDialog
import com.chico.gank.util.ToastUtils
import pl.tajchert.nammu.Nammu


/**
 * @Author: Chico
 * @Date: 2020/12/25
 * @Description:
 */
abstract class BaseFragment : Fragment(), FragmentHelperView, ToolBarView,
    FragmentOnBackStackListener {

    //Fragment的View加载完毕的标记
    var isViewCreated = false

    //Fragment对用户可见的标记
    var isUIVisible = false

    private var loadingDialog: LoadingDialog? = null

    override fun start(baseFragment: BaseFragment?) {
        start(baseFragment, -1)
    }

    override fun start(cls: Class<*>?) {
        start(cls, null)
    }

    override fun start(cls: Class<*>?, bundle: Bundle?) {
        start(cls, bundle, -1)
    }

    override fun start(baseFragment: BaseFragment?, requestCode: Int) {
        start(baseFragment, BaseActivity::class.java, null, requestCode)
    }

    override fun start(cls: Class<*>?, bundle: Bundle?, requestCode: Int) {
        start(null, cls, bundle, requestCode)
    }

    override fun start(
        baseFragment: BaseFragment?,
        cls: Class<*>?,
        bundle: Bundle?,
        requestCode: Int
    ) {
        if (requestCode != -1 && activity is BaseActivity) {
            (activity as BaseActivity?)!!.setResultFragment(this)
        }
        FragmentHelper.start(activity, baseFragment, cls, bundle, requestCode)
    }

    override fun getToolBarTitle(): TextView? {
        return if (activity is ToolBarView) {
            (activity as ToolBarView?)!!.toolBarTitle
        } else null
    }

    override fun setToolbar(hasToolbar: Boolean) {
        setToolbar(hasToolbar, null)
    }

    override fun setToolbar(hasToolbar: Boolean, title: String?) {
        setToolbar(hasToolbar, title, -1)
    }

    override fun setToolbar(hasToolbar: Boolean, title: String?, backImg: Int) {
        setToolbar(hasToolbar, title, backImg, -1)
    }

    override fun setToolbar(
        hasToolbar: Boolean,
        title: String?,
        backImg: Int,
        backgroundColor: Int
    ) {
        setToolbar(hasToolbar, title, backImg, -1, -1)
    }

    override fun setToolbar(
        hasToolbar: Boolean,
        title: String?,
        backImg: Int,
        backgroundColor: Int,
        titleColor: Int
    ) {
        if (activity is ToolBarView) {
            (activity as ToolBarView?)!!.setToolbar(
                hasToolbar,
                title,
                backImg,
                backgroundColor,
                titleColor
            )
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    protected abstract fun getIntentData()

    protected abstract fun getContentViewId(): Int

    protected abstract fun initFragment()

    protected open fun setViewLayout(view: View?) {}

    protected fun isBinding(): Boolean {
        return false
    }

    protected fun setBinding(binding: ViewDataBinding?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            getIntentData()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (isBinding()) {
            val binding: ViewDataBinding =
                DataBindingUtil.inflate(inflater, getContentViewId(), container, false)
            setBinding(binding)
            setHasOptionsMenu(true)
            binding.root
        } else {
            val rootView: View = inflater.inflate(getContentViewId(), null)
            setViewLayout(rootView)
            setHasOptionsMenu(true)
            rootView
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is BaseActivity) {
            (activity as BaseActivity?)!!.setFragmentOnBackStackListener(this)
        }
        isViewCreated = true
        lazyLoad()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isUIVisible = true
            lazyLoad()
        } else {
            isUIVisible = false
        }
    }

    private fun lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            initFragment()
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false
            isUIVisible = false
        }
    }

    /*是否有menu*/
    protected open fun hasMenu(): Boolean {
        return false
    }


    /*获取menuId*/
    protected open fun getMenuId(): Int {
        return R.menu.menu_default
    }

    /*菜单设置*/
    protected open fun menuSet(menu: Menu) {
        val menuItem = menu.findItem(R.id.menu_id)
        menuSet(menuItem)
    }

    /*菜单设置*/
    protected open fun menuSet(item: MenuItem?) {}

    /*菜单点击*/
    protected open fun menuClick(id: Int) {
        if (id == R.id.menu_id) {
            menuClick()
        }
    }

    /*菜单点击*/
    protected open fun menuClick() {}

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (hasMenu()) {
            inflater.inflate(getMenuId(), menu)
            menuSet(menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (hasMenu()) {
            menuClick(item.itemId)
        }
        return super.onOptionsItemSelected(item)
    }


    open fun showToast(message: String?) {
        ToastUtils.showShortToast(activity, message)
    }

    protected open fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(activity)
        }
        loadingDialog?.show(activity)
    }

    protected open fun dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog?.dismiss()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}