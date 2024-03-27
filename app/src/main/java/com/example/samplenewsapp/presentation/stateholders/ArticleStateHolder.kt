package com.example.samplenewsapp.presentation.stateholders

import com.example.samplenewsapp.domain.model.list.Article


data class ArticleStateHolder (
    val isLoading:Boolean = false,
    val data : List<Article>? = null,
    val error : String = ""
)