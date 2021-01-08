package com.chico.gank.model

import java.io.Serializable

/**
 * @Author: Chico
 * @Date: 2021/1/4
 * @Description:
 */
data class Article(
    var _id: String,
    var author: String,
    var category: String,
    var createdAt: String,
    var desc: String,
    var images: List<String>? = null,
    var likeCounts: Int,
    var publishedAt: String,
    var stars: Int,
    var title: String,
    var type: String,
    var url: String,
    var views: String
) : Serializable {

    fun cover(): String {
        return if (images != null && images?.isNotEmpty()!!) {
            images?.get(0) ?: ""
        } else {
            ""
        }
    }
}