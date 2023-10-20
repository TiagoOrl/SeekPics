package com.assemblermaticstudio.mistergifs.model.image

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "image")
data class Image(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("photographer")
    val by: String,
    @SerializedName("avg_color")
    val avgColor: String,
    @Embedded
    @SerializedName("src")
    val source: Source,
    @SerializedName("alt")
    val title: String,
    var fav: Boolean = false
)
