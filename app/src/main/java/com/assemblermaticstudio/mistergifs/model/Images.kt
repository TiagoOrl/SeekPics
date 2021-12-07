package com.assemblermaticstudio.mistergifs.model

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "images")
data class Images(
    @SerializedName("original")
    @Embedded
    val original: Original
)
