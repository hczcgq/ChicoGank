package com.chico.gank.model

import java.io.Serializable

/**
 * @Author: Chico
 * @Date: 2020/12/28
 * @Description:
 */
data class Banner(
    var image: String,
    var title: String,
    var url: String
) : Serializable