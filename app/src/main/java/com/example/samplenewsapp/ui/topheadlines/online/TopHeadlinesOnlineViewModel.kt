package com.example.samplenewsapp.ui.topheadlines.online

import androidx.lifecycle.viewModelScope
import com.example.samplenewsapp.data.model.ApiArticle
import com.example.samplenewsapp.data.repository.TopHeadlinesRepository
import com.example.samplenewsapp.ui.base.BaseViewModel
import com.example.samplenewsapp.ui.base.UiState
import com.example.samplenewsapp.utils.Constants
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
class TopHeadlinesOnlineViewModel @Inject constructor(
    private val topHeadlinesRepository: TopHeadlinesRepository,
    private val networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider,
    private val resourceProvider: ResourceProvider,
    private val logger: Logger
) : BaseViewModel() {
    companion object {
        const val TAG = "TopHeadlineOnlineViewModel"
    }

    private val _uiState = MutableStateFlow<UiState<List<ApiArticle>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<ApiArticle>>> = _uiState

    init {
        fetchNewsOnline()
    }

    fun fetchNewsOnline() {
        viewModelScope.launch(dispatcherProvider.main) {
            if (networkHelper.isNetworkConnected()) {
                val errorText = resourceProvider.getStringNoInternetAvailable()
                _uiState.value = UiState.Error(errorText)
                return@launch
            } else {
                topHeadlinesRepository.getTopHeadlinesOnline(Constants.DEFAULT_COUNTRY)
                    .flowOn(dispatcherProvider.io)
                    .catch { e ->
                        _uiState.value = UiState.Error(e.toString())
                        logger.d(TAG, "fetchNews: ${e.message.toString()}")
                    }.collect {
                        _uiState.value = UiState.Success(it)
                        logger.d(TAG, it.toString())
                    }
            }
        }
    }
}