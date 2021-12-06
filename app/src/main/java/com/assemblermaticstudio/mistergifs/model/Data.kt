package com.assemblermaticstudio.mistergifs.model

import com.google.gson.annotations.SerializedName



data class Data(
    @SerializedName("data")
    val data: List<GIF>
    )
