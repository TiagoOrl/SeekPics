package com.assemblermaticstudio.mistergifs.repositories

import android.os.RemoteException
import com.assemblermaticstudio.mistergifs.model.Data
import com.assemblermaticstudio.mistergifs.services.GifServices
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException


class GifRepoAccess(
    private val service: GifServices
) {
    suspend fun querySeachText(query: String, limit: Int) = flow<Data> {
        try {
            val outList = service.getGifs(query, limit)
            emit(outList)
        }
        catch (ex: HttpException) {
            throw RemoteException(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }

    suspend fun queryTrending(limit: Int) = flow<Data> {
        try {
            val outList = service.getTrendingGifs(limit = limit)
            emit(outList)
        }
        catch (ex: HttpException) {
            throw RemoteException(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }
}