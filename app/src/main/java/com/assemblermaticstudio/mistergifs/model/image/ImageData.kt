package com.assemblermaticstudio.mistergifs.model.image

import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName("photos")
    val list: ArrayList<Image>
)