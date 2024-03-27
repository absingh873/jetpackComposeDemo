package com.example.samplenewsapp.data.repository

import android.util.Log
import com.example.samplenewsapp.BuildConfig
import com.example.samplenewsapp.common.Constants
import com.example.samplenewsapp.data.di.module.toDomain
import com.example.samplenewsapp.data.network.NetworkService
import com.example.samplenewsapp.domain.model.list.Article
import com.example.samplenewsapp.domain.repository.GetArticleRepository
import javax.inject.Inject

class  ArticleReposetoryImpl @Inject constructor(private val apiService: NetworkService) : GetArticleRepository {
     override suspend fun getArticle(articlePathKey: String): List<Article> {
         return apiService.getNewsList(articlePathKey).articles!!.toDomain()

     }
    }
