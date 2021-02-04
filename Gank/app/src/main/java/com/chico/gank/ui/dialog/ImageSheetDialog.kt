package com.chico.gank.ui.dialog

import android.os.Bundle
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
class ImageSheetDialog : BaseDialogFragment() {

    private var url: String = ""

    companion object {

        const val URL = "url"

        fun instance(url: String): ImageSheetDialog {
            val bundle = Bundle()
            bundle.putString(URL, url)
            val dialog = ImageSheetDialog()
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.dialog_image_sheet
    }

    override fun getIntent() {
        url = arguments?.getString(URL) ?: ""
    }

    override fun initDialogFragment(view: View) {


        view.tv_cancel.setOnClickListener {
            dismiss()
        }
    }
}