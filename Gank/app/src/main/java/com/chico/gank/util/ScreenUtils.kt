package com.chico.gank.util

import android.R
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.*
import android.view.ViewGroup.MarginLayoutParams
import androidx.annotation.NonNull
import kotlin.math.abs


/**
 * @ClassName: ScreenUtils
 * @Description:
 * @Author: Chico
 * @Date: 2020/3/30 10:35
 */
object ScreenUtils {

    /*获得设备屏幕密度*/
    @JvmStatic
    fun getDisplayMetrics(context: Activity): Float {
        val dm = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(dm)
        return dm.density
    }

    /*获得屏幕宽度*/
    @JvmStatic
    fun getScreenWidth(context: Context?): Int {
        val wm = context!!
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /*获得屏幕高度*/
    @JvmStatic
    fun getScreenHeight(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    /*获得状态栏的高度*/
    @JvmStatic
    fun getStatusHeight(context: Context?): Int {
        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = clazz.getField("status_bar_height")[`object`].toString().toInt()
            statusHeight = context!!.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusHeight
    }

    /*获取指定控件高度*/
    @JvmStatic
    fun getWidgetHeight(view: View): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)
        return view.measuredHeight
    }

    /*获取指定控件宽度*/
    @JvmStatic
    fun getWidgetWidth(view: View): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)
        return view.measuredWidth
    }

    /*获取当前屏幕截图，包含状态栏*/
    @JvmStatic
    fun snapShotWithStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
        view.destroyDrawingCache()
        return bp
    }

    /*获取当前屏幕截图，不包含状态栏*/
    @JvmStatic
    fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight)
        view.destroyDrawingCache()
        return bp
    }

    /*获取屏幕亮度*/
    @JvmStatic
    fun getScreenBrightness(activity: Activity): Int {
        var value = 0
        val cr = activity.contentResolver
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: SettingNotFoundException) {
        }
        return value
    }

    /*设置屏幕亮度*/
    @JvmStatic
    fun setScreenBrightness(activity: Activity, value: Int) {
        val params = activity.window.attributes
        params.screenBrightness = value / 255f
        activity.window.attributes = params
    }

    /*设置组件margin值*/
    @JvmStatic
    fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int) {
        if (v.layoutParams is MarginLayoutParams) {
            val p = v.layoutParams as MarginLayoutParams
            p.setMargins(l, t, r, b)
            v.requestLayout()
        }
    }

    /*获取虚拟按键的高度*/
    @JvmStatic
    fun getNavigationBarHeight(context: Context): Int {
        return getNavigationBarHeightCheck(context, hasNavBar(context))
    }

    /*获取虚拟按键的高度*/
    @JvmStatic
    fun getNavigationBarHeightCheck(context: Context, check: Boolean): Int {
        var result = 0
        if (check) {
            val res = context.resources
            val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    /* 检查是否存在虚拟按键栏*/
    @JvmStatic
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun hasNavBar(context: Context): Boolean {
        val res = context.resources
        val resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android")
        return if (resourceId != 0) {
            var hasNav = res.getBoolean(resourceId)
            // check override flag
            val sNavBarOverride = getNavBarOverride()
            if ("1" == sNavBarOverride) {
                hasNav = false
            } else if ("0" == sNavBarOverride) {
                hasNav = true
            }
            hasNav
        } else { // fallback
            !ViewConfiguration.get(context).hasPermanentMenuKey()
        }
    }

    /*判断虚拟按键栏是否重写*/
    @JvmStatic
    private fun getNavBarOverride(): String? {
        var sNavBarOverride: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                val c = Class.forName("android.os.SystemProperties")
                val m = c.getDeclaredMethod("get", String::class.java)
                m.isAccessible = true
                sNavBarOverride = m.invoke(null, "qemu.hw.mainkeys") as String
            } catch (e: Throwable) {
            }
        }
        return sNavBarOverride
    }

    /*获取actionbar高度*/
    @JvmStatic
    fun getActionBarHeight(context: Context?): Float {
        val actionbarSizeTypedArray = context!!.obtainStyledAttributes(intArrayOf(R.attr.actionBarSize))
        val actionBarHeight2 = actionbarSizeTypedArray.getDimension(0, 0f)
        actionbarSizeTypedArray.recycle()
        return actionBarHeight2
    }

    /*判断是否是全面屏*/
    @JvmStatic
    fun checkIsFullScreen(context: Context): Boolean { // 低于 API 21的，都不会是全面屏。。。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false
        }
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val point = Point()
        display.getRealSize(point)
        val width: Float
        val height: Float
        if (point.x < point.y) {
            width = point.x.toFloat()
            height = point.y.toFloat()
        } else {
            width = point.y.toFloat()
            height = point.x.toFloat()
        }
        if (height / width >= 1.97f) {
            return true
        }
        return false
    }

    /*设置夜间模式*/
    @JvmStatic
    fun setNightMode(activity: Activity): View {
        val nightViewParam = WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        /*or WindowManager.LayoutParams.FLAG_FULLSCREEN */ or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                PixelFormat.TRANSPARENT)
        nightViewParam.width = ViewGroup.LayoutParams.MATCH_PARENT
        nightViewParam.height = ViewGroup.LayoutParams.MATCH_PARENT
        nightViewParam.gravity = Gravity.CENTER
        val nightView = View(activity)
        nightView.setBackgroundColor(0x80000000.toInt())
        activity.windowManager.addView(nightView, nightViewParam)
        return nightView
    }

    /*关闭夜间模式*/
    @JvmStatic
    fun removeNightMode(activity: Activity, view: View?) = view?.let { activity.windowManager.removeViewImmediate(view) }

    @JvmStatic
    fun checkScreenIsNotch(activity: Activity?): Boolean {
        if (activity == null) return false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val displayCutout = activity.window.decorView.rootWindowInsets.displayCutout
            if (displayCutout != null) {
                // 说明有刘海屏
                return true
            } else {
                // 通过其他方式判断是否有刘海屏  目前官方提供有开发文档的就 小米，vivo，华为（荣耀），oppo
                val manufacturer = Build.MANUFACTURER
                if (TextUtils.isEmpty(manufacturer)) {
                    return false
                }
                return when {
                    manufacturer.equals("HUAWEI", true) -> hasNotchHw(activity)
                    manufacturer.equals("xiaomi", true) -> hasNotchXiaoMi(activity)
                    manufacturer.equals("oppo", true) -> hasNotchOPPO(activity)
                    manufacturer.equals("vivo", true) -> hasNotchVIVO(activity)
                    else -> false
                }
            }
        }
        return false
    }

    /*判断vivo是否有刘海屏*/
    @JvmStatic
    fun hasNotchVIVO(activity: Activity): Boolean {
        return try {
            val cls = Class.forName("android.util.FtFeature");
            val get = cls.getMethod("isFeatureSupport", Int::class.java)
            get.invoke(cls, 0x20) as Boolean
        } catch (e: Exception) {
            false
        }
    }

    /*判断oppo是否有刘海屏*/
    @JvmStatic
    fun hasNotchOPPO(activity: Activity): Boolean {
        return activity!!.packageManager.hasSystemFeature("com.oppo.feature.screen.heteromorphism")
    }

    /*判断xiaomi是否有刘海屏*/
    @JvmStatic
    fun hasNotchXiaoMi(activity: Activity): Boolean {
        return try {
            val cls = Class.forName("android.os.SystemProperties")
            val get = cls.getMethod("getInt", String::class.java, Int::class.java)
            get.invoke(cls, "ro.miui.notch", 0) == 1
        } catch (e: Exception) {
            false
        }
    }

    /*判断华为是否有刘海屏*/
    @JvmStatic
    fun hasNotchHw(activity: Activity): Boolean {
        return try {
            val load = activity.classLoader
            val util = load.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get = util.getMethod("hasNotchInScreen")
            get.invoke(util) as Boolean
        } catch (e: Exception) {
            false
        }
    }

    // 判断是否开启了 “屏幕自动旋转”,true则为开启
    @JvmStatic
    fun isScreenAutoRotate(context: Context): Boolean {
        var gravity = 0
        try {
            gravity = Settings.System.getInt(context.contentResolver,
                    Settings.System.ACCELEROMETER_ROTATION)
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
        }
        return gravity == 1
    }

    /**
     * 判断虚拟导航栏是否显示
     *
     * @param context 上下文对象
     * @param window  当前窗口
     * @return true(显示虚拟导航栏)，false(不显示或不支持虚拟导航栏)
     */
    @JvmStatic
    fun checkNavigationBarShow(@NonNull context: Context, @NonNull window: Window): Boolean {
        val show: Boolean
        val display = window.windowManager.defaultDisplay
        val point = Point()
        display.getRealSize(point)
        val decorView = window.decorView
        val conf: Configuration = context.resources.configuration
        show = if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
            val contentView: View = decorView.findViewById(R.id.content)
            point.x != contentView.width
        } else {
            val rect = Rect()
            decorView.getWindowVisibleDisplayFrame(rect)
            rect.bottom != point.y
        }
        return show
    }


    /*获取屏幕坐标*/
    @JvmStatic
    fun getScreenPoint(activity: Activity?): Point {
        //设置悬浮窗口长宽数据
        val display = activity?.windowManager?.defaultDisplay
        val point = Point()
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display?.getRealSize(point)
        } else {
            // 不可能有虚拟按键
            display?.getSize(point);
        }
        return point
    }
}