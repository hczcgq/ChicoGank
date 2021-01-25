package com.chico.gank.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chico.gank.R
import com.chico.gank.base.BaseFragment
import com.chico.gank.base.TabStatePageAdapter
import com.chico.gank.util.*
import kotlinx.android.synthetic.main.base_toolbar.*
import kotlinx.android.synthetic.main.fragment_article.*

/**
 * @Author: Chico
 * @Date: 2021/1/7
 * @Description: 文章
 */
class ArticleFragment : BaseFragment() {

    private var titles: Array<String>? = null
    private var fragments = arrayListOf<Fragment>()

    override fun getIntentData() {

    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_article
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        userVisibleHint = true
        super.onActivityCreated(savedInstanceState)
    }

    override fun initFragment() {
        toolbar_title.text = "文章"
        toolbar.inflateMenu(R.menu.menu_search)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_search) {
                start(SearchFragment.instance(CATEGORY_ARTICLE))
            }
            false
        }

        titles = resources.getStringArray(R.array.tab_article)

        fragments.add(CategoryFragment.instance(CATEGORY_ARTICLE, ARTICLE_ANDROID))
        fragments.add(CategoryFragment.instance(CATEGORY_ARTICLE, ARTICLE_IOS))
        fragments.add(CategoryFragment.instance(CATEGORY_ARTICLE, ARTICLE_FLUTTER))
        fragments.add(CategoryFragment.instance(CATEGORY_ARTICLE, ARTICLE_FRONT_END))
        fragments.add(CategoryFragment.instance(CATEGORY_ARTICLE, ARTICLE_BACK_END))
        fragments.add(CategoryFragment.instance(CATEGORY_ARTICLE, ARTICLE_APP))

        val adapter = TabStatePageAdapter(childFragmentManager, fragments, titles)
        view_page.adapter = adapter
        view_page.offscreenPageLimit = adapter.count
        view_page.clearAnimation()
        tab_layout.setViewPager(view_page, titles)
    }
}