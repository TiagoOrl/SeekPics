package com.assemblermaticstudio.mistergifs.services

import com.assemblermaticstudio.mistergifs.model.image.ImageData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PexelsService {

    @GET("v1/search")
    suspend fun searchImages(
        @Query("query") query: String,
        @Query("orientation") orientation: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("locale") locale: String,
        @Header("Authorization") auth: String
    ) : ImageData

    @GET("v1/curated")
    suspend fun getCurated(
        @Query("per_page") perPage: Int,
        @Header("Authorization") auth: String
    ): ImageData
}