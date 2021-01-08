package com.chico.gank.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chico.gank.R
import com.chico.gank.databinding.ItemGirlBinding
import com.chico.gank.model.Article

/**
 * @Author: Chico
 * @Date: 2021/1/7
 * @Description:
 */
class GirlAdapter(data: List<Article>) :
    BaseQuickAdapter<Article, GirlAdapter.ViewHolder>(R.layout.item_girl, data) {

    override fun convert(helper: ViewHolder, item: Article?) {
        val binding = helper.binding
        binding.data = item
        binding.executePendingBindings()
    }

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding: ItemGirlBinding =
            DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)
        val view = binding.root
        view.setTag(R.id.item_girl, binding)
        return view
    }


    class ViewHolder(view: View) : BaseViewHolder(view) {
        val binding: ItemGirlBinding
            get() = itemView.getTag(R.id.item_girl) as ItemGirlBinding
    }
}