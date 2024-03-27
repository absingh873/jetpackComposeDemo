package com.example.samplenewsapp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplenewsapp.common.Resource
import com.example.samplenewsapp.domain.usecases.GetArticleUseCase
import com.example.samplenewsapp.presentation.stateholders.ArticleStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val articleUseCase: GetArticleUseCase): ViewModel() {

    private val _articleListStateHolder = mutableStateOf(ArticleStateHolder())
    val articleListStateHolder : State<ArticleStateHolder> = _articleListStateHolder

    init {
        getArticleList()
    }

    private fun getArticleList() = viewModelScope.launch {

        articleUseCase().onEach {
                when(it){
                    is Resource.Loading -> {
                        _articleListStateHolder.value = ArticleStateHolder(isLoading = true)
                    }
                    is Resource.Success -> {
                        _articleListStateHolder.value = ArticleStateHolder(data = it.data)
                    }
                    is Resource.Error -> {
                        _articleListStateHolder.value = ArticleStateHolder(error= it.message.toString())
                    }

                }
            }.launchIn(viewModelScope)

        }
    }