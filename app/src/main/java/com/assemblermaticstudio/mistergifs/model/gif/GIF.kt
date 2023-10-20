package com.assemblermaticstudio.mistergifs.model.gif

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName



@Entity
data class GIF(
    @SerializedName("id")
    @PrimaryKey
    val id: String,
    @SerializedName("embed_url")
    val embedUrl: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("images")
    @Embedded
    val images: Images,

    var fav: Boolean = false
)
