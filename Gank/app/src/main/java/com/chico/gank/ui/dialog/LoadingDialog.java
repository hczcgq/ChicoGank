package com.chico.gank.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.chico.gank.R;



/**
 * @author 陈国权
 * @date 2015/9/10
 * @description 加载圆形进度
 */
public class LoadingDialog {
    private Dialog dialog;
    private ImageView mLoadingImage;

    public LoadingDialog(Context mContext) {
        View mDialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        mLoadingImage = mDialogView.findViewById(R.id.iv_loading);
        mLoadingImage.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dialog_loading));

        if (dialog != null) {
            dialog.cancel();
        }

        dialog = new Dialog(mContext, R.style.LoadingDialogTheme);
        dialog.setContentView(mDialogView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }

    /**
     * 显示对话框
     */
    public void show(Context mContext) {
        if (dialog == null) {
            return;
        }
        mLoadingImage.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dialog_loading));
        dialog.show();
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        if (dialog == null) {
            return;
        }
        mLoadingImage.clearAnimation();
        dialog.dismiss();
    }
}
