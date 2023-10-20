package com.assemblermaticstudio.mistergifs.utils

import java.util.Locale

object Helpers {
    fun getLocale(): String {
        val lang = Locale.getDefault().language
        val country = Locale.getDefault().country

        return "${lang}-${country}"
    }
}