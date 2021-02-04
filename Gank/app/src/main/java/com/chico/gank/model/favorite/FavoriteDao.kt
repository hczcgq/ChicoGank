package com.chico.gank.model.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * @Author: Chico
 * @Date: 2021/2/4
 * @Description:
 */
@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE id LIKE :id")
    fun loadAllById(id: String): List<Favorite>?

    @Delete
    fun deleteFavorite(favorite: Favorite)

    @Insert
    fun insertAll(favorite: Favorite)
}