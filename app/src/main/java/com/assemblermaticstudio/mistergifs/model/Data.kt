package com.assemblermaticstudio.mistergifs.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("embed_url")
    val embed_url: String,
    @SerializedName("title")
    val title: String
)
