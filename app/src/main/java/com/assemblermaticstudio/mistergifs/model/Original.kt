package com.assemblermaticstudio.mistergifs.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "original")
data class Original(
    @SerializedName("url")
    val url: String
)
