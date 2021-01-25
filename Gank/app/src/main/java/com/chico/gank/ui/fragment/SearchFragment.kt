package com.chico.gank.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chico.gank.R
import com.chico.gank.base.BaseFragment
import com.chico.gank.base.BaseViewModelFragment
import com.chico.gank.http.GankViewModel
import com.chico.gank.model.Article
import com.chico.gank.ui.adapter.ArticleAdapter
import com.chico.gank.util.CATEGORY_ALL
import com.chico.gank.util.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_catalog.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.recycler_view

/**
 * @Author: Chico
 * @Date: 2021/1/18
 * @Description:搜索
 */
class SearchFragment : BaseViewModelFragment<GankViewModel>() {

    private var category: String = CATEGORY_ALL

    private var adapter: ArticleAdapter? = null

    companion object {
        const val CATEGORY = "category"
        fun instance(category: String): SearchFragment {
            val bundle = Bundle()
            bundle.putString(CATEGORY, category)
            val fragment = SearchFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getIntentData() {
        category = arguments?.getString(CATEGORY) ?: CATEGORY_ALL
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_search
    }

    override fun initFragment() {
        super.initFragment()
        setToolbar(false)
        StatusBarUtil.setTransparentForImageViewInFragment(activity, null)
        StatusBarUtil.setLightMode(activity)

        search_edit.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || (event != null && KeyEvent.KEYCODE_ENTER == event.keyCode && KeyEvent.ACTION_DOWN == event.action)
            ) {
                (activity?.application?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    search_edit.windowToken,
                    0
                )
                search()
                search_edit.clearFocus()
            }
            true
        }

        search_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(s)) {
                    iv_clear.visibility = View.GONE
                } else {
                    iv_clear.visibility = View.VISIBLE
                }
            }
        })

        iv_clear.setOnClickListener {
            search_edit.setText("")
            setArticleAdapter(emptyList())
        }

        search_cancel.setOnClickListener {
            activity?.finish()
        }

        viewmodel?.article?.observe(this, Observer {
            dismissLoading()
            setArticleAdapter(it)
        })
    }

    private fun search() {
        val key = search_edit.text.toString()
        if (TextUtils.isEmpty(key)) {
            showToast("搜索内容不能为空")
            return
        }

        showLoading()
        viewmodel?.search(key, category)
    }

    private fun setArticleAdapter(it: List<Article>?) {
        val data = it ?: arrayListOf()
        if (adapter == null) {
            adapter = ArticleAdapter(data)
            recycler_view.adapter = adapter
            recycler_view.layoutManager = LinearLayoutManager(activity)
        } else {
            adapter?.setNewData(data)
        }
    }
}