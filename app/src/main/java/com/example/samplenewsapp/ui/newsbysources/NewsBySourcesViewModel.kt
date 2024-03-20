package com.example.samplenewsapp.ui.newsbysources

import androidx.lifecycle.viewModelScope
import com.example.samplenewsapp.data.model.ApiArticle
import com.example.samplenewsapp.data.repository.NewsBySourcesRepository
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsBySourcesViewModel @Inject constructor(
    private val newsBySourcesRepository: NewsBySourcesRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val resourceProvider: ResourceProvider,
    private val logger: Logger
) : BaseViewModel() {

    companion object {
        const val TAG = "NewsBySourcesViewModel"
    }

    private val _uiState = MutableStateFlow<UiState<List<ApiArticle>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<ApiArticle>>> = _uiState

    fun fetchNewsBySources(sourceName: String?) {
        viewModelScope.launch(dispatcherProvider.main) {
            if (!networkHelper.isNetworkConnected()) {
                val errorText = resourceProvider.getStringNoInternetAvailable()
                _uiState.value = UiState.Error(errorText)
                return@launch
            } else {
                logger.d(TAG, "fetchNewsBySources: $sourceName")
                _uiState.value = UiState.Loading
                val replaceString = sourceName?.replace("{", "")?.replace("}", "")
                logger.d(TAG, "replaceString: $replaceString")
                sourceName?.let { it ->
                    newsBySourcesRepository.getNewsBySources(it) //pass source
                        .catch { e ->
                            _uiState.value = UiState.Error(e.toString())
                            logger.d(
                                TAG, "fetchNewsBySources: ${e.message.toString()}"
                            )
                        }.collect {
                            _uiState.value = UiState.Success(it)
                            logger.d(TAG, it.toString())

                        }
                }
            }
        }
    }

    fun fetchNewsByCountry(countryCode: String?) {
        viewModelScope.launch(dispatcherProvider.main) {
            logger.d(TAG, "fetchNewsByCountry: $countryCode")
            _uiState.value = UiState.Loading
            countryCode?.let { it ->
                newsBySourcesRepository.getNewsByCountry(it) //pass countryCode
                    .catch { e ->
                        _uiState.value = UiState.Error(e.toString())
                        logger.d(
                            TAG,
                            "fetchNewsByCountry: ${e.message.toString()}"
                        )
                    }.collect {
                        _uiState.value = UiState.Success(it)
                    }
            }
        }
    }

    fun fetchNewsByLanguage(languageId: String?) {
        viewModelScope.launch(dispatcherProvider.main) {
            logger.d(TAG, "fetchNewsByLanguage: languageId: $languageId")
            _uiState.value = UiState.Loading
            languageId?.let { id ->
                newsBySourcesRepository.getNewsByLanguage(id) // pass languageId
                    .catch { e ->
                        _uiState.value = UiState.Error(e.toString())
                        logger.d(
                            TAG,
                            "fetchNewsByLanguage: error: ${e.message.toString()}"
                        )
                    }.collect {
                        _uiState.value = UiState.Success(it)
                        logger.d(TAG, it.toString())
                    }
            }

        }
    }

}