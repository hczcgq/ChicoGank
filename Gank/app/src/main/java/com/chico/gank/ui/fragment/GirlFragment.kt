package com.chico.gank.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chico.gank.R
import com.chico.gank.base.BaseViewModelFragment
import com.chico.gank.http.GankViewModel
import com.chico.gank.model.Article
import com.chico.gank.ui.adapter.ArticleAdapter
import com.chico.gank.ui.adapter.GirlAdapter
import kotlinx.android.synthetic.main.base_toolbar.*
import kotlinx.android.synthetic.main.fragment_catalog.*

/**
 * @Author: Chico
 * @Date: 2021/1/7
 * @Description:妹子
 */
class GirlFragment : BaseViewModelFragment<GankViewModel>() {

    private var category: String = "Girl"
    private var type: String = "Girl"
    private var page: Int = 1

    private var adapter: GirlAdapter? = null

    override fun getIntentData() {

    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_girl
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        userVisibleHint = true
        super.onActivityCreated(savedInstanceState)
    }

    override fun initFragment() {
        super.initFragment()
        toolbar_title.text = "妹子"

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
            adapter = GirlAdapter(data)
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