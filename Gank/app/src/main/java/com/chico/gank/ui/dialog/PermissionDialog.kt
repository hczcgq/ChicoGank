package com.chico.gank.ui.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.chico.gank.R
import kotlinx.android.synthetic.main.dialog_permission.view.*

/**
 * @ClassName: PermissionDialog
 * @Description:
 * @Author: Chico
 * @Date: 2019/12/16 11:30
 */
class PermissionDialog : BaseDialogFragment() {

    private var msg: String = ""

    companion object {

        const val MSG = "msg"

        fun instance(msg: String): PermissionDialog {
            val bundle = Bundle()
            bundle.putString(MSG, msg)
            val dialog = PermissionDialog()
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.dialog_permission
    }

    override fun getIntent() {
        msg = requireArguments().getString(MSG)!!
    }

    override fun initDialogFragment(view: View) {
        view.tv_msg.text = msg

        view.tv_cancel.setOnClickListener {
            dismiss()
        }

        view.tv_allow.setOnClickListener {
            dismiss()
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.fromParts("package", requireActivity().packageName, null)
            startActivity(intent)
        }
    }

    override fun getGravity(): Int {
        return CENTER
    }
}