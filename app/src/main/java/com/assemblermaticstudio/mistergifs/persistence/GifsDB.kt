package com.assemblermaticstudio.mistergifs.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assemblermaticstudio.mistergifs.model.gif.GIF

@Database(entities = [GIF::class], version = 1)
abstract class GifsDB : RoomDatabase() {
    abstract fun gifsDao() : GifsDAO
}