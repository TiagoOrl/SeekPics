package com.assemblermaticstudio.mistergifs.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.assemblermaticstudio.mistergifs.model.GIF

@Dao
interface GifsDAO {
    @Insert(onConflict = IGNORE)
    fun insertAll(gifs_list: List<GIF>)

    @Query("SELECT * FROM gif")
    fun loadAllGifs(): List<GIF>

    @Query("SELECT * FROM gif" +
        " WHERE fav = 1")
    fun getFavGifs(): List<GIF>

//    @Update
//    suspend fun update(gif: GIF)
}