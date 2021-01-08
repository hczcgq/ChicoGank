package com.chico.gank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.chico.gank.base.FragmentHelper
import com.chico.gank.ui.MainFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity() {

    //倒计时订阅者
    private var subscribe: Disposable? = null

    private var destroy: Boolean = false

    private var stop: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        App.systemRecycleStatus = 0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (show != 0L) {
            count = show
            countDownTime()
        } else {
            start()
        }
    }

    /*倒计时*/
    var count: Long = 5
    var show = 5L
    private fun countDownTime() {
        tv_time.setOnClickListener {
            start()
        }

        tv_time.visibility = View.VISIBLE
        if (subscribe == null) {
            subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (stop) return@subscribe
                    show = count - it
                    if (show <= 0.toLong()) {
                        start()
                        return@subscribe
                    }
                    tv_time.text = String.format(getString(R.string.splash_count_down), show)
                }
        }
    }

    /*停止倒计时*/
    private fun stopCountDownTime() {
        stop = true
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
    }

    /*跳转*/
    private fun start() {
        if (destroy) {
            return
        }
        destroy = true
        stopCountDownTime()
        FragmentHelper.start(this, MainFragment())
        finish()
    }
}