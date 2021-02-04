package com.chico.gank.ui.dialog

import android.view.View
import com.chico.gank.App
import com.chico.gank.R
import com.chico.gank.util.FileUtils
import com.chico.gank.util.ToastUtils
import kotlinx.android.synthetic.main.dialog_clean_cache.view.*

/**
 * @ClassName: CleanCacheDialog
 * @Description:
 * @Author: Chico
 * @Date: 2019/9/5 13:42
 */
class CleanCacheDialog : BaseDialogFragment() {

    override fun getContentViewId(): Int {
        return R.layout.dialog_clean_cache
    }

    override fun getIntent() {

    }

    override fun initDialogFragment(view: View) {
        view.tv_cache_size.text = String.format(
            getString(R.string.cache_size),
            FileUtils.getTotalCacheSize(requireContext())
        )
        view.tv_clean_cache.setOnClickListener {
            FileUtils.clearAllCache(requireContext())
            ToastUtils.showShortToast(requireActivity(), getString(R.string.clean_success))
            dismiss()
        }
        view.tv_cancel.setOnClickListener {
            dismiss()
        }
    }
}