package com.assemblermaticstudio.mistergifs.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.assemblermaticstudio.mistergifs.model.GIF
import kotlinx.coroutines.flow.Flow

@Dao
interface GifsDAO {

    @Insert(onConflict = IGNORE)
    fun insertAll(gifs_list: List<GIF>)

    @Query("SELECT * FROM gif")
    fun loadAllGifs(): List<GIF>

}