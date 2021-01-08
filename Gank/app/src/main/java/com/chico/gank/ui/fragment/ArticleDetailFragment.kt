package com.chico.gank.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import com.chico.gank.R

import com.chico.gank.base.BaseViewModelFragment
import com.chico.gank.http.GankViewModel

/**
 * @Author: Chico
 * @Date: 2021/1/7
 * @Description:
 */
class ArticleDetailFragment : BaseViewModelFragment<GankViewModel>() {

    private var postId: String? = null

    companion object {
        const val ID = "id"
        fun instance(id: String): ArticleDetailFragment {
            val bundle = Bundle()
            bundle.putString(ID, id)
            val fragment = ArticleDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getIntentData() {
        postId = arguments?.getString(ID)
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_article_detail
    }

    override fun initFragment() {
        super.initFragment()
        setToolbar(true, "详情")

        if (TextUtils.isEmpty(postId)) {
            activity?.finish()
            return
        }
    }
}