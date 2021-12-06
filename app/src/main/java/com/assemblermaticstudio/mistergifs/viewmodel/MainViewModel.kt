package com.assemblermaticstudio.mistergifs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assemblermaticstudio.mistergifs.model.Gif
import com.assemblermaticstudio.mistergifs.repositories.GifRepoAccess
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val gifRepoAccess: GifRepoAccess) : ViewModel() {

    private val _output = MutableLiveData<State>()
    val output: LiveData<State> = _output

    fun searchGif(name: String, limit: Int) {
        viewModelScope.launch {
            gifRepoAccess.querySeachText(name, limit)
                .onStart { _output.postValue(State.Loading) }
                .catch { _output.postValue(State.Error(it)) }
                .collect { _output.postValue(State.Success(it)) }
        }
    }

    fun getTrendingGifs(limit: Int) {
        viewModelScope.launch {
            gifRepoAccess.queryTrending(limit)
                .onStart { _output.postValue(State.Loading) }
                .catch { _output.postValue(State.Error(it)) }
                .collect { _output.postValue(State.Success(it)) }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val gifObject: Gif) : State()
        data class Error(val error: Throwable) : State()
    }
}