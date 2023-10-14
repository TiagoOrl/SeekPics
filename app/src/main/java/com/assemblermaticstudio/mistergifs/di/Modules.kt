package com.assemblermaticstudio.mistergifs.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.assemblermaticstudio.mistergifs.persistence.GifsDAO
import com.assemblermaticstudio.mistergifs.persistence.GifsDB
import com.assemblermaticstudio.mistergifs.repositories.GifRepoAccess
import com.assemblermaticstudio.mistergifs.services.GifServices
import com.assemblermaticstudio.mistergifs.viewmodel.MainViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Modules {

    private const val OK_HTTP = "OkHttp"

    fun load(applicationContext: Context) {
        loadKoinModules(createRoomService(applicationContext) + networkModules() + repositoryModules() + presentationModules())
    }

    private fun networkModules(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(OK_HTTP, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createRetrofitService<GifServices>(get(), get())
            }
        }
    }
    private fun repositoryModules() : Module {
        return module {
            single<GifRepoAccess> {GifRepoAccess(get(), get())}
        }
    }

    private fun presentationModules() : Module {
        return module {
            viewModel { MainViewModel(get()) }
        }
    }

    private fun createRoomService(context: Context) : Module {
        return module {
            single<GifsDAO> {
                val db = Room.databaseBuilder(
                    context,
                    GifsDB::class.java, "gifs"
                ).build()
                db.gifsDao()
            }
        }
    }



    private inline fun <reified T> createRetrofitService(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): T {
        return Retrofit.Builder()
            .baseUrl("https://api.giphy.com/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build().create(T::class.java)
    }



}