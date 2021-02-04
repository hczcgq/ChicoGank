package com.chico.gank.ui.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.chico.gank.R
import com.chico.gank.base.BaseViewModelFragment
import com.chico.gank.http.GankViewModel
import com.chico.gank.model.ArticleDetail
import com.chico.gank.model.favorite.Favorite
import com.chico.gank.model.favorite.FavoriteDatabase
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_article_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.INTEGER


/**
 * @Author: Chico
 * @Date: 2021/1/7
 * @Description:
 */
class ArticleDetailFragment : BaseViewModelFragment<GankViewModel>() {

    private var postId: String? = null
    private var detail: ArticleDetail? = null

    private var favoriteMenu: MenuItem? = null
    private var isFavorite: Boolean = false

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

        viewmodel?.favoriteStatus?.observe(this, Observer { exit ->
            if (exit != null) {
                isFavorite = exit
                favoriteMenu?.let {
                    it.setIcon(if (exit) R.drawable.icon_favorited else R.drawable.icon_favorite)
                }
            } else {
                showToast("操作异常")
            }
        })

        viewmodel?.favoriteDeal?.observe(this, Observer { status ->
            if (status != null) {
                favoriteMenu?.let {
                    isFavorite = status
                    it.setIcon(if (status) R.drawable.icon_favorited else R.drawable.icon_favorite)
                    showToast(if (status) "收藏成功" else "取消收藏")
                }
            } else {
                showToast("操作异常")
            }
        })
    }

    override fun hasMenu(): Boolean {
        return true
    }

    override fun getMenuId(): Int {
        return R.menu.menu_article
    }

    override fun menuSet(menu: Menu) {
        super.menuSet(menu)
        favoriteMenu = menu.findItem(R.id.menu_favorite)
        viewmodel?.checkIsFavorite(requireActivity(), postId ?: "")
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
            R.id.menu_favorite -> {
                detail?.let {
                    val image =
                        if (it.images != null && it.images!!.isNotEmpty()) it.images!![0] else ""
                    val favorite =
                        Favorite(it._id, it.author, image, it.title, it.type, it.publishedAt)
                    viewmodel?.insertFavorite(requireActivity(), favorite)
                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        if (!isFavorite) {
            val intent = Intent()
            intent.putExtra("id", id)
            requireActivity().setResult(Activity.RESULT_OK, intent)
        }
        return super.onBackPressed()
    }
}