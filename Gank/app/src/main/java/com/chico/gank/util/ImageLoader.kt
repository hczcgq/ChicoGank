package com.chico.gank.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.io.File
import java.util.concurrent.ExecutionException


object ImageLoader {

    @JvmStatic
    fun loadImage(context: Context, id: Any, view: ImageView) {
        loadImage(context, id, view, null, null)
    }

    @JvmStatic
    fun loadImage(context: Context, id: Any, view: ImageView, placeholder: Int) {
        val drawable = ContextCompat.getDrawable(context, placeholder)
        loadImage(context, id, view, drawable)
    }

    @JvmStatic
    fun loadImage(context: Context, id: Any, view: ImageView, placeholder: Int, transform: Transformation<Bitmap>?) {
        val drawable = ContextCompat.getDrawable(context, placeholder)
        loadImage(context, id, view, drawable, transform, transform != null)
    }

    @JvmStatic
    fun loadImage(context: Context, id: Any, view: ImageView, placeholder: Drawable?) {
        loadImage(context, id, view, placeholder, CenterCrop())
    }

    @JvmStatic
    fun loadImage(context: Context, id: Any, view: ImageView, placeholder: Drawable?, transform: Transformation<Bitmap>?, anim: Boolean? = true) {
        val request = Glide.with(context)
                .load(id)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder)
                .error(placeholder)
        transform?.let {
            request.transform(it)
        }
        if (anim == true) {
            request.transition(DrawableTransitionOptions.with(DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build()))
        } else {
            request.dontAnimate()
        }
        request.into(view)
    }

    @JvmStatic
    fun loadRoundImage(context: Context, id: Any, view: ImageView) {
        Glide.with(context)
                .load(id)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop(), RoundedCorners(4))
                .transition(DrawableTransitionOptions.with(DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build()))
                .into(view)
    }

    @JvmStatic
    fun loadRoundImage(context: Context, id: Any, corners: Int, view: ImageView) {
        Glide.with(context)
                .load(id)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop(), RoundedCorners(corners))
                .transition(DrawableTransitionOptions.with(DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build()))
                .into(view)
    }

    @JvmStatic
    fun loadRoundImage(context: Context, id: Any, corners: Int, view: ImageView, listener: RequestListener<Drawable>) {
        Glide.with(context)
                .load(id)
                .listener(listener)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop(), RoundedCorners(corners))
                .transition(DrawableTransitionOptions.with(DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build()))
                .into(view)
    }

    @JvmStatic
    fun loadRoundImage(context: Context, id: Any, corners: Int, view: ImageView, placeholder: Drawable) {
        Glide.with(context)
                .load(id)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop(), RoundedCorners(corners))
                .transition(DrawableTransitionOptions.with(DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build()))
                .placeholder(placeholder)
                .error(placeholder)
                .into(view)
    }

    @JvmStatic
    fun loadRoundImage(context: Context, id: Any, view: ImageView, placeholder: Int) {
        val drawable = ContextCompat.getDrawable(context, placeholder)
        loadRoundImage(context, id, view, drawable!!)
    }


    @JvmStatic
    fun loadRoundImage(context: Context, id: Any, view: ImageView, placeholder: Drawable) {
        Glide.with(context)
                .load(id)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop(), RoundedCorners(4))
                .transition(DrawableTransitionOptions.with(DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build()))
                .placeholder(placeholder)
                .error(placeholder)
                .into(view)
    }

    @JvmStatic
    fun loadRoundImage(context: Context, id: Any, view: ImageView, placeholder: Int, radius: Int) {
        Glide.with(context)
                .load(id)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop(), RoundedCorners(radius))
                .transition(DrawableTransitionOptions.with(DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build()))
                .placeholder(placeholder)
                .error(placeholder)
                .into(view)
    }

    @JvmStatic
    fun loadRoundImage(context: Context, id: Any, view: ImageView, placeholder: Int, radius: Int, location: RoundedCornersTransformation.CornerType) {
        Glide.with(context)
                .load(id)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop(), RoundedCornersTransformation(radius, 0, location))
                .transition(DrawableTransitionOptions.with(DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build()))
                .placeholder(placeholder)
                .error(placeholder)
                .into(view)
    }


    @JvmStatic
    fun loadCircleImage(context: Context, id: Any, view: ImageView) {
        Glide.with(context)
                .load(id)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop(), CircleCrop())
                .transition(DrawableTransitionOptions.with(DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build()))
                .into(view)
    }

    @JvmStatic
    fun loadCircleImage(context: Context, id: Any, view: ImageView, placeholder: Int) {
        val drawable = ContextCompat.getDrawable(context, placeholder)
        loadCircleImage(context, id, view, drawable!!)
    }


    @JvmStatic
    fun loadCircleImage(context: Context, id: Any, view: ImageView, placeholder: Drawable) {
        Glide.with(context)
                .load(id)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop(), CircleCrop())
                .transition(DrawableTransitionOptions.with(DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build()))
                .placeholder(placeholder)
                .error(placeholder)
                .into(view)
    }

    @JvmStatic
    fun loadTarget(context: Context, id: Any, target: SimpleTarget<Drawable>) {
        Glide.with(context)
                .load(id)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop(), CircleCrop())
                .transition(DrawableTransitionOptions.with(DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build()))
                .into(target)
    }

    /*获取图片路径*/
    @JvmStatic
    fun getImagePath(context: Context?, imgUrl: String?): String? {
        var path: String? = null
        val future: FutureTarget<File> = Glide.with(context!!)
                .load(imgUrl)
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        try {
            val cacheFile: File = future.get()
            path = cacheFile.absolutePath
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
        return path
    }
}