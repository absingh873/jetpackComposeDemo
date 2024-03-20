package com.example.samplenewsapp.ui.topheadlines.paging

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.samplenewsapp.data.model.ApiArticle
import com.example.samplenewsapp.data.repository.TopHeadlinesRepository
import com.example.samplenewsapp.ui.base.BaseViewModel
import com.example.samplenewsapp.utils.DispatcherProvider
import com.example.samplenewsapp.utils.ResourceProvider
import com.example.samplenewsapp.utils.logger.Logger
import com.example.samplenewsapp.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesPagingViewModel @Inject constructor(
    private val topHeadlinesRepository: TopHeadlinesRepository,
    private val networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider,
    private val resourceProvider: ResourceProvider,
    private val logger: Logger
) : BaseViewModel() {
    companion object {
        const val TAG = "TopHeadlinesPagingViewModel"
    }

    private val _uiState = MutableStateFlow<PagingData<ApiArticle>>(value = PagingData.empty())

    val uiState: StateFlow<PagingData<ApiArticle>> = _uiState

    init {
        fetchNewsPaging()
    }

    private fun fetchNewsPaging() {
        viewModelScope.launch {
            if (!networkHelper.isNetworkConnected()) {
                return@launch
            } else {
                topHeadlinesRepository.getTopHeadlinesPgn()
                    .flowOn(dispatcherProvider.io)
                    .collect {
                        _uiState.value = it
                        logger.d(TAG, it.toString())
                    }
            }
        }
    }
}