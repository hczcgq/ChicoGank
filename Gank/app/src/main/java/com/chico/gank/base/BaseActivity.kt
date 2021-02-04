package com.chico.gank.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chico.gank.App
import com.chico.gank.R
import com.chico.gank.SplashActivity
import com.chico.gank.util.StatusBarUtil
import com.chico.gank.util.ToastUtils
import kotlinx.android.synthetic.main.base_toolbar.*

/**
 * @Author: Chico
 * @Date: 2020/12/24
 * @Description:
 */
open class BaseActivity : AppCompatActivity(), ToolBarView {

    companion object {
        const val INTENT_FRAGMENT = "Fragment"
    }

    private var resultFragment: Fragment? = null
    private var fragmentOnBackStackListener: FragmentOnBackStackListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (App.systemRecycleStatus == -1) {
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
            return
        }
        setContentView(R.layout.activity_base)
        initActivity()
        addFragmentContain()
    }

    open fun getContentViewId(): Int {
        return -1
    }

    open fun initActivity() {}

    private fun addFragmentContain() {
        if (getContentViewId() != -1) {
            return
        }

        try {
            val baseFragment =
                Class.forName(intent.getStringExtra(INTENT_FRAGMENT)).newInstance() as Fragment
            if (baseFragment.isAdded) {
                return
            }
            if (intent != null) {
                baseFragment.arguments = intent.extras
            }
            resultFragment = baseFragment
            val ft =
                supportFragmentManager.beginTransaction()
            ft.add(R.id.layout_contain, baseFragment, baseFragment.javaClass.simpleName)
            ft.commitAllowingStateLoss()
            baseFragment.userVisibleHint = true
            baseFragment.setMenuVisibility(true)
            StatusBarUtil.setColor(this, resources.getColor(R.color.toolbar), 1)
            StatusBarUtil.setLightMode(this)
        } catch (e: Exception) {
            ToastUtils.showShortToast(this, e.message)
            finish()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    open fun setResultFragment(resultFragment: Fragment?) {
        this.resultFragment = resultFragment
    }

    override fun onBackPressed() {
        if (fragmentOnBackStackListener != null && !fragmentOnBackStackListener?.onBackPressed()!!) {
            return
        }
        super.onBackPressed()
    }

    open fun setFragmentOnBackStackListener(fragmentOnBackStackListener: FragmentOnBackStackListener?) {
        if (this.fragmentOnBackStackListener != null) {
            return  //由第一个fragment进行事件分发
        }
        this.fragmentOnBackStackListener = fragmentOnBackStackListener
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultFragment != null) {
            resultFragment?.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
    }

    override fun getToolBarTitle(): TextView {
        return toolbar_title
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
        setToolbar(hasToolbar, title, backImg, backgroundColor, -1)
    }

    override fun setToolbar(
        hasToolbar: Boolean,
        title: String?,
        backImg: Int,
        backgroundColor: Int,
        titleColor: Int
    ) {
        if (!hasToolbar) {
            toolbar.visibility = View.GONE
            return
        }
        setSupportActionBar(toolbar)
        supportActionBar!!.displayOptions = (ActionBar.DISPLAY_HOME_AS_UP
                or ActionBar.DISPLAY_SHOW_HOME)

        if (backgroundColor != -1) {
            toolbar.setBackgroundColor(backgroundColor)
        }

        if (backImg != -1) {
            toolbar.setNavigationIcon(backImg)
        } else {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }

        if (titleColor != -1) {
            toolbar_title.setTextColor(titleColor)
        }

        if (TextUtils.isEmpty(title)) {
            toolbar_title.text = ""
        } else {
            toolbar_title.text = Html.fromHtml(title)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        resultFragment!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}