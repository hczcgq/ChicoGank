package com.chico.gank.model.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: Chico
 * @Date: 2021/2/4
 * @Description:
 */
@Entity
data class Favorite(
    @PrimaryKey val id: String,
    var author: String,
    var image: String,
    var title: String,
    var type: String,
    var publishedAt: String
)