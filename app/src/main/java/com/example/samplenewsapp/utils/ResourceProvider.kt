package com.example.samplenewsapp.utils

import android.content.Context
import com.example.samplenewsapp.R

interface ResourceProvider {
    fun getStringNoInternetAvailable(): String
}

class DefaultResourceProvider(
    private val context: Context
) : ResourceProvider {
    override fun getStringNoInternetAvailable(): String {
        return context.getString(R.string.no_internet)
    }

}