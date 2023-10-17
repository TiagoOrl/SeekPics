package com.assemblermaticstudio.mistergifs.model.gif

import com.google.gson.annotations.SerializedName

data class GifData(
    @SerializedName("data")
    val list: ArrayList<GIF>
)
