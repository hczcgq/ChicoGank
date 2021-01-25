package com.chico.gank.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.chico.gank.R
import com.chico.gank.base.BaseViewModelFragment
import com.chico.gank.http.GankViewModel
import com.chico.gank.model.ArticleDetail
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_article_detail.*


/**
 * @Author: Chico
 * @Date: 2021/1/7
 * @Description:
 */
class ArticleDetailFragment : BaseViewModelFragment<GankViewModel>() {

    private var postId: String? = null
    private var detail: ArticleDetail? = null

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
        setToolbar(true, "详情", R.drawable.back_white_icon)

        if (TextUtils.isEmpty(postId)) {
            activity?.finish()
            return
        }

        showLoading()
        viewmodel?.getArticleDetail(postId ?: "")

        viewmodel?.articleDetail?.observe(this, Observer {
            this.detail = it
            RichText.fromMarkdown(it?.markdown).into(tv_content)
            dismissLoading()
        })
    }

    override fun hasMenu(): Boolean {
        return true
    }

    override fun getMenuId(): Int {
        return R.menu.menu_article
    }

    override fun menuClick(id: Int) {
        super.menuClick(id)
        when (id) {
            R.id.menu_share -> {
                val shareContent = "${detail?.title}:${detail?.url}"
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "*/*"
                intent.putExtra(Intent.EXTRA_TEXT, shareContent)
                activity?.startActivity(intent)
            }
        }
    }
}