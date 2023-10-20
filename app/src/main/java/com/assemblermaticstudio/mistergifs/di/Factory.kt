package com.assemblermaticstudio.mistergifs.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.assemblermaticstudio.mistergifs.persistence.GifsDB
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Factory {
    inline fun <reified T> createRetrofitService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        baseUrl: String
    ): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build().create(T::class.java)
    }

    inline fun <reified T: RoomDatabase> createDatabaseInstance(context: Context, name: String): T {
        return Room.databaseBuilder(
            context,
            T::class.java, name
        ).build()
    }
}