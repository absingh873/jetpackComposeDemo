package com.example.samplenewsapp.ui.search

import androidx.lifecycle.viewModelScope
import com.example.samplenewsapp.data.model.ApiArticle
import com.example.samplenewsapp.data.repository.SearchRepository
import com.example.samplenewsapp.ui.base.BaseViewModel
import com.example.samplenewsapp.ui.base.UiState
import com.example.samplenewsapp.utils.Constants.DEBOUNCE_TIMEOUT
import com.example.samplenewsapp.utils.Constants.MIN_SEARCH_CHAR
import com.example.samplenewsapp.utils.DispatcherProvider
import com.example.samplenewsapp.utils.ResourceProvider
import com.example.samplenewsapp.utils.logger.Logger
import com.example.samplenewsapp.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val resourceProvider: ResourceProvider,
    private val logger: Logger
) : BaseViewModel() {

    companion object {
        const val TAG = "SearchViewModel"
    }

    private val _uiState =
        MutableStateFlow<UiState<List<ApiArticle>>>(UiState.Success(emptyList()))

    val uiState: StateFlow<UiState<List<ApiArticle>>> = _uiState

    private val searchText = MutableStateFlow("")

    init {
        setUpSearchStateFlow()
    }

    fun searchNews(searchQuery: String) {
        searchText.value = searchQuery
    }

    private fun setUpSearchStateFlow() {
        viewModelScope.launch {
            if (!networkHelper.isNetworkConnected()) {
                val errorText = resourceProvider.getStringNoInternetAvailable()
                _uiState.value = UiState.Error(errorText)
                return@launch
            } else {
                searchText.debounce(DEBOUNCE_TIMEOUT)
                    .filter { query ->
                        if (query.isNotEmpty() && query.length >= MIN_SEARCH_CHAR) {
                            return@filter true
                        } else {
                            _uiState.value = UiState.Success(emptyList())
                            return@filter false
                        }
                    }
                    .distinctUntilChanged()
                    .flatMapLatest {
                        _uiState.value = UiState.Loading
                        return@flatMapLatest searchRepository.getNewsBySearch(it)
                            .catch { e ->
                                // handle error
                                _uiState.value = UiState.Error(e.toString())
                                logger.d(
                                    "SearchViewModel",
                                    "fetchNewsBySearch: error: ${e.message.toString()} "
                                )
                            }
                    }
                    .flowOn(dispatcherProvider.io)
                    .collect {
                        // handle response and empty response properly
                        _uiState.value = UiState.Success(it)
                        logger.d(TAG, it.toString())
                    }
            }
        }

    }

}