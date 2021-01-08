package com.chico.gank.model

import android.content.res.ColorStateList

/**
 * @Author: Chico
 * @Date: 2021/1/4
 * @Description:
 */
data class TabItem(
    val title: String,
    val titleColor: ColorStateList,
    val selectIcon: Int,
    val unSelectIcon: Int,
    val index: Int
)