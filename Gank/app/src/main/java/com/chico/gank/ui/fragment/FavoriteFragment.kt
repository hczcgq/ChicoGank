package com.chico.gank.ui.fragment

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chico.gank.R
import com.chico.gank.base.BaseViewModelFragment
import com.chico.gank.http.GankViewModel
import com.chico.gank.model.favorite.Favorite
import com.chico.gank.ui.adapter.FavoriteAdapter
import kotlinx.android.synthetic.main.fragment_catalog.*

/**
 * @Author: Chico
 * @Date: 2021/2/4
 * @Description: 收藏
 */
class FavoriteFragment : BaseViewModelFragment<GankViewModel>() {

    private var adapter: FavoriteAdapter? = null

    override fun getIntentData() {

    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_favorite
    }


    override fun initFragment() {
        super.initFragment()
        setToolbar(true, "我的收藏", R.drawable.back_white_icon)

        swipe_layout.setOnRefreshListener {
            viewmodel?.getFavorite(requireActivity())
        }

        showLoading()
        viewmodel?.getFavorite(requireActivity())

        viewmodel?.favorite?.observe(this, Observer {
            setFavoriteAdapter(it)
            dismissLoading()
            swipe_layout.isRefreshing = false
        })
    }

    private fun setFavoriteAdapter(it: List<Favorite>?) {
        val data = it ?: arrayListOf()
        if (adapter == null) {
            adapter = FavoriteAdapter(ArrayList(data))
            recycler_view.adapter = adapter
            recycler_view.layoutManager = LinearLayoutManager(activity)
        } else {
            adapter?.setNewData(ArrayList(data))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            val id = data.getStringExtra("id") ?: ""
            if (adapter != null && adapter?.data != null && adapter?.data?.isNotEmpty()!!) {
                val index = adapter?.data?.indexOfFirst { it.id == id }
                if (index != null && index >= 0) {
                    adapter?.remove(index)
                }
            }
        }
    }
}