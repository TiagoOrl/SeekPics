package com.assemblermaticstudio.mistergifs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assemblermaticstudio.mistergifs.model.Data
import com.assemblermaticstudio.mistergifs.model.GIF
import com.assemblermaticstudio.mistergifs.repositories.GifRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val gifRepository: GifRepository) : ViewModel() {

    private val _output = MutableLiveData<State>()
    val output: LiveData<State> = _output

    fun searchGif(name: String, limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            gifRepository.querySearchText(name, limit)
                .onStart { _output.postValue(State.Loading) }
                .catch { _output.postValue(State.Error(it)) }
                .collect { _output.postValue(State.Success(it)) }
        }
    }

    fun getTrendingGifs(limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            gifRepository.queryTrending(limit)
                .onStart { _output.postValue(State.Loading) }
                .catch { _output.postValue(State.Error(it)) }
                .collect { _output.postValue(State.Success(it)) }
        }
    }

    fun queryGifs() {
        viewModelScope.launch(Dispatchers.IO) {
            gifRepository.queryAllGifsFromDB()
                .onStart { _output.postValue(State.Loading) }
                .catch { _output.postValue(State.Error(it)) }
                .collect { _output.postValue(State.SuccessQueryDB(it)) }
        }
    }

    fun setAsFavourite(gif: GIF) {
        viewModelScope.launch {
            gif.fav = true
            gifRepository.addFavourite(gif)
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val dataObject: Data) : State()
        data class SuccessQueryDB(val dataObject: List<GIF>) : State()
        data class Error(val error: Throwable) : State()
    }
}
