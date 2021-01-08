package com.chico.gank.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chico.gank.R
import com.chico.gank.base.BaseViewModelFragment
import com.chico.gank.http.GankViewModel
import com.chico.gank.model.Article
import com.chico.gank.ui.adapter.ArticleAdapter
import com.chico.gank.util.CATEGORY_GAN_HUO
import com.chico.gank.util.GAN_HUO_ANDROID
import kotlinx.android.synthetic.main.fragment_catalog.*

/**
 * @Author: Chico
 * @Date: 2021/1/7
 * @Description:
 */
class CategoryFragment : BaseViewModelFragment<GankViewModel>() {

    private var category: String = CATEGORY_GAN_HUO
    private var type: String = GAN_HUO_ANDROID
    private var page: Int = 1

    private var adapter: ArticleAdapter? = null

    companion object {
        const val CATEGORY = "category"
        const val TYPE = "type"
        fun instance(category: String, type: String): CategoryFragment {
            val bundle = Bundle()
            bundle.putString(CATEGORY, category)
            bundle.putString(TYPE, type)
            val fragment = CategoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getIntentData() {
        category = arguments?.getString(CATEGORY) ?: CATEGORY_GAN_HUO
        type = arguments?.getString(TYPE) ?: GAN_HUO_ANDROID
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_catalog
    }


    override fun initFragment() {
        super.initFragment()

        swipe_layout.setOnRefreshListener {
            page = 1
            viewmodel?.getArticle(category, type, page)
        }

        viewmodel?.getArticle(category, type, page)

        viewmodel?.article?.observe(this, Observer {
            setArticleAdapter(it)
        })
    }

    private fun setArticleAdapter(it: List<Article>?) {
        val data = it ?: arrayListOf()
        if (adapter == null) {
            adapter = ArticleAdapter(data)
            recycler_view.adapter = adapter
            recycler_view.layoutManager = LinearLayoutManager(activity)
        } else {
            if (page == 1) {
                adapter?.setNewData(data)
            } else {
                adapter?.addData(data)
            }
        }

        if (page == 1) {
            swipe_layout.isRefreshing = false
        } else {
            if(data.isEmpty()){
                adapter?.loadMoreEnd()
            }else {
                adapter?.loadMoreComplete()
            }
        }

        adapter?.setOnLoadMoreListener({
            page++
            viewmodel?.getArticle(category, type, page)
        }, recycler_view)
    }
}