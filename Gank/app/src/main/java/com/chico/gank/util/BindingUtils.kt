package com.chico.gank.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * @ClassName: BindingUtils
 * @Description:
 * @Author: Chico
 * @Date: 2019/9/24 14:39
 */
object BindingUtils {

    @JvmStatic
    @BindingAdapter(value = ["date", "format"], requireAll = false)
    fun date(view: TextView?, date: Long?, format: String? = "yyyy-MM-dd") {
        if (view == null || date == null || date == 0.toLong()) return
        val sdf = SimpleDateFormat(format)
        view.text = sdf.format(Date(date * 1000))
    }

    @JvmStatic
    @BindingAdapter(value = ["date", "format"], requireAll = false)
    fun date(view: TextView?, date: Date?, format: String? = "yyyy-MM-dd") {
        if (view == null || date == null) return
        val sdf = SimpleDateFormat(format)
        view.text = sdf.format(date)
    }


    @JvmStatic
    @BindingAdapter(value = ["circle", "placeholder"], requireAll = false)
    fun circleImage(view: ImageView, url: Any?, placeholder: Drawable? = null) {
        if (url == null) return
        val context = view.context
        if (placeholder == null) {
            ImageLoader.loadCircleImage(context, url, view)
        } else {
            ImageLoader.loadCircleImage(context, url, view, placeholder)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["rounder", "placeholder"], requireAll = false)
    fun RounderImage(view: ImageView, url: Any?, placeholder: Drawable? = null) {
        if (url == null) return
        val context = view.context
        if (placeholder == null) {
            ImageLoader.loadRoundImage(context, url, view)
        } else {
            ImageLoader.loadRoundImage(context, url, view, placeholder)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["image", "placeholder"], requireAll = false)
    fun image(view: ImageView, url: Any?, placeholder: Drawable? = null) {
        if (url == null) return
        val context = view.context
        if (placeholder == null) {
            ImageLoader.loadImage(context, url, view)
        } else {
            ImageLoader.loadImage(context, url, view, placeholder)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["def", "placeholder"], requireAll = false)
    fun def(view: ImageView, url: Any?, placeholder: Drawable? = null) {
        if (url == null) return
        val context = view.context
        if (placeholder == null) {
            ImageLoader.loadImage(context, url, view, null, null, false)
        } else {
            ImageLoader.loadImage(context, url, view, placeholder = placeholder, transform = null, anim = false)
        }
    }
}