package com.chico.gank.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chico.gank.App
import com.chico.gank.R
import com.chico.gank.SplashActivity
import com.chico.gank.util.StatusBarUtil
import com.chico.gank.util.ToastUtils

/**
 * @Author: Chico
 * @Date: 2020/12/24
 * @Description:
 */
open class BaseActivity : AppCompatActivity() {

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

        setContentView(if (getContentViewId() == -1) R.layout.activity_base else getContentViewId())
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
            StatusBarUtil.setColor(this, resources.getColor(R.color.purple_500), 1)
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
}