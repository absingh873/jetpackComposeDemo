package com.example.samplenewsapp.data.repository

import com.example.samplenewsapp.data.model.Language
import com.example.samplenewsapp.utils.LANGUAGES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageRepository @Inject constructor() {

    fun getLanguages(): Flow<List<Language>> {
        return flow {
            emit(LANGUAGES)
        }
    }

}