package com.assemblermaticstudio.mistergifs.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assemblermaticstudio.mistergifs.model.image.Image

@Database(entities = [Image::class], version = 1)
abstract class ImageDB : RoomDatabase() {
    abstract fun imageDAO() : ImageDAO
}