package com.assemblermaticstudio.mistergifs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GIF(
    @SerializedName("embed_url")
    val embed_url: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("id")
    @PrimaryKey val id: String
)
