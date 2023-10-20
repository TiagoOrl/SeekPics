package com.assemblermaticstudio.mistergifs.viewmodel

sealed class State {
    object Loading : State()
    data class SuccessGet<T>(val dataRes: T) : State()
    data class SuccessQuery<T>(val list: ArrayList<T>) : State()
    object EmptyFavs: State()
    object SuccessEmpty : State()
    data class Error(val error: Throwable) : State()
}