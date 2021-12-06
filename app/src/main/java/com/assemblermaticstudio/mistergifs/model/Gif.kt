package com.assemblermaticstudio.mistergifs.model

import com.google.gson.annotations.SerializedName



data class Gif(
    @SerializedName("data")
    val data: List<Data>
    )
