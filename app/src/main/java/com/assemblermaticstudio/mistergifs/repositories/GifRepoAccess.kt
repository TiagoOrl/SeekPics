package com.assemblermaticstudio.mistergifs.repositories

import android.os.RemoteException
import com.assemblermaticstudio.mistergifs.model.Data
import com.assemblermaticstudio.mistergifs.model.GIF
import com.assemblermaticstudio.mistergifs.persistence.GifsDAO
import com.assemblermaticstudio.mistergifs.services.GifServices
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception


class GifRepoAccess(
    private val service: GifServices,
    private val dao: GifsDAO
) {
    suspend fun querySeachText(query: String, limit: Int) = flow<Data> {
        try {
            val outList = service.getGifs(query, limit)
            dao.insertAll(outList.data)
            emit(outList)
        }
        catch (ex: HttpException) {
            throw RemoteException(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }

    suspend fun queryTrending(limit: Int) = flow<Data> {
        try {
            val outList = service.getTrendingGifs(limit = limit)
            dao.insertAll(outList.data)
            emit(outList)
        }
        catch (ex: HttpException) {
            throw RemoteException(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }

    suspend fun queryAllGifsFromDB() = flow<List<GIF>> {
        try {
            val outList = dao.loadAllGifs()
            emit(outList)

        } catch (ex: Exception) {
            throw Exception(ex.message)
        }
    }
}