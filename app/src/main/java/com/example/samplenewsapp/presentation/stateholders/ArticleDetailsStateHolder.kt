package com.example.samplenewsapp.presentation.stateholders

import com.example.samplenewsapp.domain.model.detail.ArticleDetail


data class ArticleDetailsStateHolder(
    val isLoading:Boolean = false,
    val data : ArticleDetail? = null,
    val error:String = ""
)
