package com.example.samplenewsapp.data.repository

import com.example.samplenewsapp.data.api.NetworkService
import com.example.samplenewsapp.data.model.ApiSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsSourcesRepository @Inject constructor(private val networkService: NetworkService) {

    fun getNewsSources(): Flow<List<ApiSource>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map {
            it.sources
        }
    }
}