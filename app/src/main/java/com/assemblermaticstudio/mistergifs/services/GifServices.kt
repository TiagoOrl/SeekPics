package com.assemblermaticstudio.mistergifs.services

import com.assemblermaticstudio.mistergifs.model.Gif
import retrofit2.http.GET
import retrofit2.http.Query

interface GifServices {

    @GET("v1/gifs/search")
    suspend fun getGifs(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("api_key") key: String = "HSI8cCsrvwXUCxvsGvW3gXvFEoIfNJ6w",
        ): Gif

    @GET("v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("api_key") key: String = "HSI8cCsrvwXUCxvsGvW3gXvFEoIfNJ6w",
        @Query("limit") limit: Int): Gif
}