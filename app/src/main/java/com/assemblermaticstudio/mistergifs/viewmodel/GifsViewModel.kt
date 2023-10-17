package com.assemblermaticstudio.mistergifs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assemblermaticstudio.mistergifs.model.gif.GifData
import com.assemblermaticstudio.mistergifs.model.gif.GIF
import com.assemblermaticstudio.mistergifs.repositories.GifRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class GifsViewModel(private val gifRepository: GifRepository) : ViewModel() {

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

    fun getAllLocalGifs() {
        viewModelScope.launch(Dispatchers.IO) {
            gifRepository.queryAllGifsFromDB()
                .onStart { _output.postValue(State.Loading) }
                .catch { _output.postValue(State.Error(it)) }
                .collect {
                    if (it.isEmpty())
                        _output.postValue(State.SuccessEmpty)
                    else
                        _output.postValue(State.SuccessQuery(it))
                }
        }
    }

    fun toggleFavourite(gif: GIF) {
        viewModelScope.launch {
            gif.fav = !gif.fav
            gifRepository.toggleFavourite(gif)
        }
    }

    fun getFavouriteGifs() {
        viewModelScope.launch(Dispatchers.IO) {
            gifRepository.getFavGifs()
                .onStart { _output.postValue(State.Loading) }
                .catch { _output.postValue(State.Error(it)) }
                .collect{ _output.postValue(State.SuccessQuery(it)) }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val dataObject: GifData) : State()
        data class SuccessQuery(val list: ArrayList<GIF>) : State()
        object SuccessEmpty : State()
        data class Error(val error: Throwable) : State()
    }
}
