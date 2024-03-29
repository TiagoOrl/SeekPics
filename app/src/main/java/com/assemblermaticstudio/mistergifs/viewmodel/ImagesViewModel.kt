package com.assemblermaticstudio.mistergifs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assemblermaticstudio.mistergifs.model.image.Image
import com.assemblermaticstudio.mistergifs.repositories.ImageRepository
import com.assemblermaticstudio.mistergifs.model.image.ImageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ImagesViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {
    private val _output = MutableLiveData<State>()
    val output: LiveData<State> = _output

    fun searchImages(
        query: String,
        orientation: String?,
        page: Int = 1,
        perPage: Int = 15,
        locale: String = "en-US"
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.getBySearch(
                query,
                orientation,
                page,
                perPage,
                locale
            )
            .onStart { _output.postValue(State.Loading) }
            .catch { _output.postValue(State.Error(it)) }
            .collect { _output.postValue(State.SuccessGet<ImageData>(it)) }
        }
    }

    fun getCuratedImages(perPage: Int  = 15) {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.getCurated(perPage)
                .onStart { _output.postValue(State.Loading) }
                .catch { _output.postValue(State.Error(it)) }
                .collect { _output.postValue(State.SuccessGet<ImageData>(it)) }
        }
    }

    fun getAllLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.queryAllCached()
                .onStart { _output.postValue(State.Loading) }
                .catch { _output.postValue(State.Error(it)) }
                .collect{
                    if (it.isEmpty())
                        _output.postValue(State.SuccessEmpty)
                    else
                        _output.postValue(State.SuccessQuery<Image>(it))
                }
        }
    }

    fun getFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.queryFavourites()
                .onStart { _output.postValue(State.Loading) }
                .catch { _output.postValue(State.Error(it)) }
                .collect {
                    if (it.isEmpty())
                        _output.postValue(State.EmptyFavs)
                    else
                        _output.postValue(State.SuccessQuery<Image>(it))
                }
        }
    }

    fun toggleFavourite(image: Image) {
        image.fav = !image.fav
        viewModelScope.launch {
            imageRepository.toggleFavourite(image)
        }
    }
}