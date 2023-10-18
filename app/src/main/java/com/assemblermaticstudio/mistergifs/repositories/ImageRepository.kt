package com.assemblermaticstudio.mistergifs.repositories


import android.os.RemoteException
import com.assemblermaticstudio.mistergifs.model.image.Image
import kotlinx.coroutines.flow.flow
import com.assemblermaticstudio.mistergifs.model.image.ImageData
import com.assemblermaticstudio.mistergifs.persistence.ImageDAO
import com.assemblermaticstudio.mistergifs.services.PexelsService
import retrofit2.HttpException

class ImageRepository(
    private val service: PexelsService,
    private val dao: ImageDAO
) {
    private val auth = "npTHK9OOwf4xe0LaSaV2pFzSNosH3UIfUP85TuOyjFCneLa1Y0SdBazy"
    fun getBySearch(
        query: String,
        orientation: String?,
        page: Int,
        perPage: Int,
        locale: String
    ) = flow<ImageData> {
        try {
            val outList = service.searchImages(
                query,
                orientation,
                page,
                perPage,
                locale,
                auth
            )
            dao.insertAll(outList.list)
            emit(outList)
        } catch (ex: Exception) {
            throw Exception(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }

    fun getCurated(perPage: Int = 1) = flow<ImageData> {
        try {
            val outList = service.getCurated(perPage, auth)
            emit(outList)
        } catch (ex: HttpException) {
            throw Exception(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }

    fun queryAllCached() = flow<ArrayList<Image>> {
        try {
            val outList = dao.queryCached()
            emit(outList as ArrayList<Image>)
        } catch (ex: Exception) {
            throw Exception(ex.message)
        }
    }

    fun queryFavourites() = flow<ArrayList<Image>> {
        try {
            val outList = dao.queryFavs()
            emit(outList as ArrayList<Image>)
        } catch (ex: Exception) {
            throw Exception(ex.message)
        }
    }

    suspend fun toggleFavourite(image: Image) {
        dao.update(image)
    }
}