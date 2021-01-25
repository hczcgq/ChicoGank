package com.chico.gank.model

import java.io.Serializable

/**
 * @Author: Chico
 * @Date: 2021/1/12
 * @Description:
 */
data class ArticleDetail(
    var _id: String,
    var author: String,
    var category: String,
    var content: String,
    var createdAt: String,
    var desc: String,
    var email: String,
    var images: List<String>? = null,
    var index: Int,
    var isOriginal: Boolean,
    var license: String,
    var likeCounts: Int,
    var likes: List<String>? = null,
    var markdown: String,
    var originalAuthor: String,
    var publishedAt: String,
    var stars: Int,
    var status: Int,
    var tags: List<String>? = null,
    var title: String,
    var type: String,
    var updatedAt: String,
    var url: String,
    var views: String
) : Serializable