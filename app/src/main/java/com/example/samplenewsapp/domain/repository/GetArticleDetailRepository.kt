package com.example.samplenewsapp.domain.repository

import com.example.samplenewsapp.domain.model.detail.ArticleDetail
import com.example.samplenewsapp.domain.model.list.Article

interface GetArticleDetailRepository {
    suspend fun getArticleDetail(articleApiKey:String):ArticleDetail
}