package com.assemblermaticstudio.mistergifs.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.assemblermaticstudio.mistergifs.model.image.Image


@Dao
interface ImageDAO {
    @Insert(onConflict = IGNORE)
    fun insertAll(imagesList: List<Image>)

    @Query("SELECT * FROM image")
    fun queryCached(): List<Image>

    @Query("SELECT * FROM image" +
        " WHERE fav = 1")
    fun queryFavs(): List<Image>

    @Update
    suspend fun update(image: Image)
}