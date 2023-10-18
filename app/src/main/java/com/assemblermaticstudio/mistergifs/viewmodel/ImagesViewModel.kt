package com.assemblermaticstudio.mistergifs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assemblermaticstudio.mistergifs.model.image.Image
import com.assemblermaticstudio.mistergifs.model.image.ImageData
import com.assemblermaticstudio.mistergifs.repositories.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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
            .collect { _output.postValue(State.SuccessGet(it)) }
        }
    }


    sealed class State {
        object Loading : State()
        data class SuccessGet(val dataObject: ImageData) : State()
        data class SuccessQuery(val list: ArrayList<Image>) : State()
        object SuccessEmpty : State()
        data class Error(val error: Throwable) : State()
    }
}