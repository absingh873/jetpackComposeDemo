package com.example.samplenewsapp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplenewsapp.common.Resource
import com.example.samplenewsapp.domain.usecases.GetArticleDetailUseCase
import com.example.samplenewsapp.presentation.stateholders.ArticleDetailsStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(private val articleDetailsUseCase: GetArticleDetailUseCase,savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _articleDetailsStateHolder = mutableStateOf(ArticleDetailsStateHolder())
    val articleDetailsStateHolder : State<ArticleDetailsStateHolder> = _articleDetailsStateHolder

    init {
        viewModelScope.launch {

            savedStateHandle.getStateFlow("id", "660411672b1b334a633c2717").collectLatest {

                getArticleDetails(it)

            }
        }
    }

    private fun getArticleDetails(id: String) = viewModelScope.launch {

        articleDetailsUseCase(id).onEach {
            when(it){

                is Resource.Loading -> {
                    _articleDetailsStateHolder.value = ArticleDetailsStateHolder(isLoading = true)
                }

                is Resource.Success -> {
                    _articleDetailsStateHolder.value = ArticleDetailsStateHolder(data = it.data)
                }

                is Resource.Error -> {
                    _articleDetailsStateHolder.value = ArticleDetailsStateHolder(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }
}