package com.assemblermaticstudio.mistergifs.repositories

import android.os.RemoteException
import androidx.annotation.WorkerThread
import com.assemblermaticstudio.mistergifs.model.gif.GifData
import com.assemblermaticstudio.mistergifs.model.gif.GIF
import com.assemblermaticstudio.mistergifs.persistence.GifsDAO
import com.assemblermaticstudio.mistergifs.services.GifService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import kotlin.Exception


class GifRepository(
    private val service: GifService,
    private val dao: GifsDAO
) {
    fun getByText(query: String, limit: Int) = flow<GifData> {
        try {
            val outList = service.getGifs(query, limit)
            dao.insertAll(outList.list)
            emit(outList)
        }
        catch (ex: HttpException) {
            throw RemoteException(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }

    fun getTrending(limit: Int) = flow<GifData> {
        try {
            val outList = service.getTrendingGifs(limit = limit)
            dao.insertAll(outList.list)
            emit(outList)
        }
        catch (ex: HttpException) {
            throw RemoteException(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }

    fun queryAllGifsFromDB() = flow<ArrayList<GIF>> {
        try {
            val outList = dao.loadAllGifs()
            emit(outList as ArrayList<GIF>)

        } catch (ex: Exception) {
            throw Exception(ex.message)
        }
    }

    fun queryFavGifs() = flow<ArrayList<GIF>> {
        try {
            val outList = dao.queryFavGifs()
            emit(outList as ArrayList<GIF>)
        } catch (ex: Exception) {
            throw Exception(ex.message)
        }
    }

    @WorkerThread
    suspend fun toggleFavourite(gif: GIF) {
        dao.update(gif)
    }


}