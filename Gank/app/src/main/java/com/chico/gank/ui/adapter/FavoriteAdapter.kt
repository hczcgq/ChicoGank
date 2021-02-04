package com.chico.gank.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chico.gank.R
import com.chico.gank.base.FragmentHelper
import com.chico.gank.databinding.ItemArticleBinding
import com.chico.gank.databinding.ItemFavoriteBinding
import com.chico.gank.model.Article
import com.chico.gank.model.favorite.Favorite
import com.chico.gank.ui.fragment.ArticleDetailFragment

/**
 * @Author: Chico
 * @Date: 2021/1/4
 * @Description:
 */
class FavoriteAdapter(data: List<Favorite>) :
    BaseQuickAdapter<Favorite, FavoriteAdapter.ViewHolder>(R.layout.item_favorite, data) {

    override fun convert(helper: ViewHolder, item: Favorite?) {
        val binding = helper.binding
        binding.data = item
        helper.itemView.setOnClickListener {
            FragmentHelper.start(
                mContext,
                ArticleDetailFragment.instance(item!!.id),
                requestCode = 100
            )
        }
        binding.executePendingBindings()
    }

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding: ItemFavoriteBinding =
            DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)
        val view = binding.root
        view.setTag(R.id.item_favorite, binding)
        return view
    }


    class ViewHolder(view: View) : BaseViewHolder(view) {
        val binding: ItemFavoriteBinding
            get() = itemView.getTag(R.id.item_favorite) as ItemFavoriteBinding
    }
}