package com.assemblermaticstudio.mistergifs.di

import android.content.Context
import android.util.Log
import com.assemblermaticstudio.mistergifs.persistence.GifsDAO
import com.assemblermaticstudio.mistergifs.persistence.GifsDB
import com.assemblermaticstudio.mistergifs.persistence.ImageDAO
import com.assemblermaticstudio.mistergifs.persistence.ImageDB
import com.assemblermaticstudio.mistergifs.repositories.GifRepository
import com.assemblermaticstudio.mistergifs.services.GifService
import com.assemblermaticstudio.mistergifs.services.PexelsService
import com.assemblermaticstudio.mistergifs.viewmodel.GifsViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

import retrofit2.converter.gson.GsonConverterFactory

object Modules {

    private const val OK_HTTP = "OkHttp"

    fun load(applicationContext: Context) {
        loadKoinModules(
    databaseModules(applicationContext) +
            networkModules() +
            webServicesModules() +
            repositoryModules() +
            viewModelModules()
        )
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
        }
    }



    private fun webServicesModules(): Module {
        return module {
            single {
                Factory.createRetrofitService<GifService>(get(), get(), "https://api.giphy.com/")
            }
            single {
                Factory.createRetrofitService<PexelsService>(get(), get(), "https://api.pexels.com/")
            }
        }
    }
    private fun repositoryModules() : Module {
        return module {
            single<GifRepository> { GifRepository(get(), get()) }
        }
    }

    private fun viewModelModules() : Module {
        return module {
            viewModel {
                GifsViewModel(get())
            }
        }
    }

    private fun databaseModules(context: Context) : Module {
        return module {
            single<GifsDAO> {
                val db = Factory.createDatabaseInstance<GifsDB>(context, "gifs")
                db.gifsDao()
            }
            single<ImageDAO> {
                val db = Factory.createDatabaseInstance<ImageDB>(context, "image")
                db.imageDAO()
            }
        }
    }
}