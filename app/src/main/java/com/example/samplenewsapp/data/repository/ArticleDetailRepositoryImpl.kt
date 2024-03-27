package com.example.samplenewsapp.data.repository

import com.example.samplenewsapp.data.di.module.toArticleDetail
import com.example.samplenewsapp.data.di.module.toDomain
import com.example.samplenewsapp.data.model.detail.ArticleDetailDTO
import com.example.samplenewsapp.data.network.NetworkService
import com.example.samplenewsapp.domain.model.detail.ArticleDetail
import com.example.samplenewsapp.domain.model.list.Article
import com.example.samplenewsapp.domain.repository.GetArticleDetailRepository
import javax.inject.Inject


class  ArticleDetailRepositoryImpl @Inject constructor(private val apiService: NetworkService) : GetArticleDetailRepository {
    override suspend fun getArticleDetail(articleApiKey: String): ArticleDetail {
        return apiService.getArticle(articleApiKey).toDomain()
    }
}
