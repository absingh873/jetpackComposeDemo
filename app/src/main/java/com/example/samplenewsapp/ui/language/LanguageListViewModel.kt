package com.example.samplenewsapp.ui.language

import androidx.lifecycle.viewModelScope
import com.example.samplenewsapp.data.model.Language
import com.example.samplenewsapp.data.repository.LanguageRepository
import com.example.samplenewsapp.ui.base.BaseViewModel
import com.example.samplenewsapp.ui.base.UiState
import com.example.samplenewsapp.utils.DispatcherProvider
import com.example.samplenewsapp.utils.ResourceProvider
import com.example.samplenewsapp.utils.logger.Logger
import com.example.samplenewsapp.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageListViewModel @Inject constructor(
    private val languageRepository: LanguageRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val resourceProvider: ResourceProvider,
    private val logger: Logger
) : BaseViewModel() {

    companion object {
        const val TAG = "LanguageListViewModel"
    }

    private val _uiState = MutableStateFlow<UiState<List<Language>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Language>>> = _uiState

    init {
        fetchLanguages()
    }

    private fun fetchLanguages() {
        viewModelScope.launch {
            if (!networkHelper.isNetworkConnected()) {
                val errorText = resourceProvider.getStringNoInternetAvailable()
                _uiState.value = UiState.Error(errorText)
                return@launch
            } else {
                _uiState.value = UiState.Loading
                languageRepository.getLanguages()
                    .flowOn(dispatcherProvider.io)
                    .catch { e ->
                        _uiState.value = UiState.Error(e.toString())
                        logger.d(TAG, "fetchLanguages: ${e.message.toString()}")
                    }.collect {
                        _uiState.value = UiState.Success(it)
                        logger.d(TAG, it.toString())
                    }
            }
        }
    }

}