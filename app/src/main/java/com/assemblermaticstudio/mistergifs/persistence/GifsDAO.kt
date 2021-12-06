package com.assemblermaticstudio.mistergifs.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.assemblermaticstudio.mistergifs.model.GIF
import kotlinx.coroutines.flow.Flow

@Dao
interface GifsDAO {

    @Insert(onConflict = REPLACE)
    fun addGIF(gif: GIF)

    @Query("SELECT * FROM gif WHERE id = :id")
    fun load(id: String): Flow<List<GIF>>

}