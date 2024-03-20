package com.example.samplenewsapp.data.repository

import com.example.samplenewsapp.data.model.Country
import com.example.samplenewsapp.utils.COUNTRIES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountryRepository @Inject constructor() {

    fun getCountries(): Flow<List<Country>> {
        return flow {
            emit(COUNTRIES)
        }
    }

}