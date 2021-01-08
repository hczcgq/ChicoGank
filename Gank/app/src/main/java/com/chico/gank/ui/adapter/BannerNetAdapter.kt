package com.chico.gank.ui.adapter


import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chico.gank.R
import com.chico.gank.model.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils


/**
 * @Author: Chico
 * @Date: 2021/1/4
 * @Description:
 */
class BannerNetAdapter(data: List<Banner>) :
    BannerAdapter<Banner, BannerNetAdapter.ImageHolder>(data) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder? {
        val imageView: ImageView = BannerUtils.getView(parent!!, R.layout.banner_image) as ImageView
        //通过裁剪实现圆角
        BannerUtils.setBannerRound(imageView, 20f)
        return ImageHolder(imageView)
    }

    override fun onBindView(
        holder: ImageHolder,
        data: Banner,
        position: Int,
        size: Int
    ) {
        //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
        holder.imageView?.let {
            Glide.with(holder.itemView)
                .load(data.image)
                .into(it)
        }
    }

    class ImageHolder(view: ImageView) : RecyclerView.ViewHolder(view) {

        var imageView: ImageView? = null

        init {
            this.imageView = view
        }
    }
}