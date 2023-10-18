package com.assemblermaticstudio.mistergifs.model.image

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "source")
data class Source (
    @SerializedName("original")
    val original: String,
    @SerializedName("large2x")
    val large2x: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("tiny")
    val tiny: String
)