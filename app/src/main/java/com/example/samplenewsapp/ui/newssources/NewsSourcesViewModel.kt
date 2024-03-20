package com.example.samplenewsapp.ui.newssources

import androidx.lifecycle.viewModelScope
import com.example.samplenewsapp.data.model.ApiSource
import com.example.samplenewsapp.data.repository.NewsSourcesRepository
import com.example.samplenewsapp.ui.base.BaseViewModel
import com.example.samplenewsapp.ui.base.UiState
import com.example.samplenewsapp.utils.ResourceProvider
import com.example.samplenewsapp.utils.logger.Logger
import com.example.samplenewsapp.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsSourcesViewModel @Inject constructor(
    private val newsSourcesRepository: NewsSourcesRepository,
    private val networkHelper: NetworkHelper,
    private val resourceProvider: ResourceProvider,
    private val logger: Logger
) : BaseViewModel() {
    companion object {
        const val TAG = "NewsSourcesViewModel"
    }

    private val _uiState = MutableStateFlow<UiState<List<ApiSource>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<ApiSource>>> = _uiState

    init {
        fetchNewsSources()
    }

    private fun fetchNewsSources() {
        viewModelScope.launch {
            if (!networkHelper.isNetworkConnected()) {
                val errorText = resourceProvider.getStringNoInternetAvailable()
                _uiState.value = UiState.Error(errorText)
                return@launch
            } else {
                newsSourcesRepository.getNewsSources().catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                    logger.d(TAG, "fetchNewsSources: ${e.message.toString()}")
                }.collect {
                    _uiState.value = UiState.Success(it)
                    logger.d(TAG, it.toString())
                }
            }
        }
    }
}