package com.chico.gank.ui.dialog


import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.chico.gank.R
import pl.tajchert.nammu.Nammu

/**
 * @ClassName: BaseDialogFragment
 * @Description:
 * @Author: Chico
 * @Date: 2020/3/18 10:09
 */
abstract class BaseDialogFragment : DialogFragment(), DialogInterface.OnKeyListener,
    DialogInterface.OnCancelListener {

    companion object {
        const val DEFAULT = -1
        const val BOTTOM = 0
        const val CENTER = 1
    }

    private var mDismissListener: OnDismissListener? = null

    protected var isFirst = true

    protected abstract fun getContentViewId(): Int

    protected abstract fun getIntent()

    protected abstract fun initDialogFragment(view: View)

    //对话框展示位置
    protected open fun getGravity(): Int {
        return BOTTOM
    }

    protected open fun getWindowsAnimations(): Int {
        return R.style.DialogAlphaStyle
    }

    protected open fun dimAmount(): Float {
        return 0f
    }

    protected open fun setCancelable(): Boolean {
        return true
    }

    protected open fun setCanceledOnTouchOutside(): Boolean {
        return setCancelable()
    }

    protected open fun hasSoftware(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle()
        getIntent()
    }

    open fun setStyle() {
        setStyle(STYLE_NORMAL, android.R.style.Theme_Dialog)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog!!.setOnCancelListener(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view =
            LayoutInflater.from(requireActivity()).inflate(getContentViewId(), container, false)
        initDialogFragment(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        if (isFirst) {
            isFirst = false
            setStartStyle()
        }
    }

    open fun setStartStyle() {
        if (dialog != null) {
            dialog!!.setOnKeyListener(this)
            dialog!!.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (hasSoftware()) {
                //解决弹出输入法抖动问题
                dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }
            dialog!!.setCancelable(setCancelable())
            dialog!!.setCanceledOnTouchOutside(setCanceledOnTouchOutside())
            val window = dialog!!.window
            if (getGravity() == BOTTOM) {
                window!!.setWindowAnimations(R.style.DialogBottomStyle)
                val wl = window.attributes
                if (dimAmount() > 0) {
                    wl.dimAmount = dimAmount()
                }
                wl.x = 0
                wl.y = requireActivity().windowManager.defaultDisplay.height
                wl.width = ViewGroup.LayoutParams.MATCH_PARENT
                wl.height = ViewGroup.LayoutParams.WRAP_CONTENT
                dialog!!.onWindowAttributesChanged(wl)
            } else if (getGravity() == CENTER) {
                window!!.setWindowAnimations(getWindowsAnimations())
                val wl = window.attributes
                if (dimAmount() > 0) {
                    wl.dimAmount = dimAmount()
                }
                val displayMetrics = requireActivity().resources.displayMetrics
                if (window.decorView.height >= (displayMetrics.heightPixels * 0.7).toInt()) {
                    wl.height = (displayMetrics.heightPixels * 0.7).toInt()
                }
                dialog!!.onWindowAttributesChanged(wl)
            }
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        if (mDismissListener != null) {
            mDismissListener!!.onDismiss()
        }
        super.onDismiss(dialog)
    }

    override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN && dialog != null) {
            dialog.dismiss()
        }
        return false
    }

    open fun setOnDismissListener(listener: OnDismissListener): BaseDialogFragment {
        mDismissListener = listener
        return this
    }

    interface OnDismissListener {
        fun onDismiss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroyView() {
        dialog?.setOnDismissListener(null)
        dialog?.setOnCancelListener(null)
        super.onDestroyView()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft: FragmentTransaction = manager.beginTransaction()
            if (this.isAdded) {
                ft.remove(this).commit()
            }
            ft.add(this, tag ?: "")
            ft.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}