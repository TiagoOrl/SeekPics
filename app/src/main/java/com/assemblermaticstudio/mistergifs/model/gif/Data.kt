package com.assemblermaticstudio.mistergifs.model.gif

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    val data: ArrayList<GIF>
)
