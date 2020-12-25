package com.chico.gank.base;


import android.widget.TextView;

/**
 * @author Chico Chen
 * @date 2018/6/29
 * @description
 */
public interface ToolBarView {

    void setToolbar(boolean hasToolbar);

    void setToolbar(boolean hasToolbar, String title);

    void setToolbar(boolean hasToolbar, String title, int backImg);

    void setToolbar(boolean hasToolbar, String title, int backImg, int backgroundColor);

    void setToolbar(boolean hasToolbar, String title, int backImg, int backgroundColor, int titleColor);

    TextView getToolBarTitle();
}
