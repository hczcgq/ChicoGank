package com.chico.gank.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chico.gank.R
import com.chico.gank.base.FragmentHelper
import com.chico.gank.databinding.ItemArticleBinding
import com.chico.gank.model.Article
import com.chico.gank.ui.fragment.ArticleDetailFragment

/**
 * @Author: Chico
 * @Date: 2021/1/4
 * @Description:
 */
class ArticleAdapter(data: List<Article>) :
    BaseQuickAdapter<Article, ArticleAdapter.ViewHolder>(R.layout.item_article, data) {

    override fun convert(helper: ViewHolder, item: Article?) {
        val binding = helper.binding
        binding.data = item
        helper.itemView.setOnClickListener {
            FragmentHelper.start(mContext, ArticleDetailFragment.instance(item!!._id))
        }
        binding.executePendingBindings()
    }

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding: ItemArticleBinding =
            DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)
        val view = binding.root
        view.setTag(R.id.item_article, binding)
        return view
    }


    class ViewHolder(view: View) : BaseViewHolder(view) {
        val binding: ItemArticleBinding
            get() = itemView.getTag(R.id.item_article) as ItemArticleBinding
    }
}