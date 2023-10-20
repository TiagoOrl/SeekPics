package com.assemblermaticstudio.mistergifs.services

import com.assemblermaticstudio.mistergifs.model.gif.GifData
import retrofit2.http.GET
import retrofit2.http.Query

interface GifService {
    @GET("v1/gifs/search")
    suspend fun getGifs(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("api_key") key: String,
    ): GifData

    @GET("v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("limit") limit: Int,
        @Query("api_key") key: String
    ): GifData
}