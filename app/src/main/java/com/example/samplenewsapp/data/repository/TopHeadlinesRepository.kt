package com.example.samplenewsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.samplenewsapp.data.api.NetworkService
import com.example.samplenewsapp.data.local.database.DatabaseService
import com.example.samplenewsapp.data.local.entity.Article
import com.example.samplenewsapp.data.model.ApiArticle
import com.example.samplenewsapp.data.model.toArticleEntity
import com.example.samplenewsapp.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlinesRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun getTopHeadlinesOnline(country: String): Flow<List<ApiArticle>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
        }
    }

    // Offline
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTopHeadlinesOffline(country: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles.map { article -> article.toArticleEntity() }
        }.flatMapConcat { articles ->
            flow { emit(databaseService.deleteAllInsertAll(articles)) }
        }.flatMapConcat {
            databaseService.getArticles()
        }
    }

    fun getArticlesDirectlyFromDB(): Flow<List<Article>> {
        return databaseService.getArticles()
    }

    fun getTopHeadlinesPgn(): Flow<PagingData<ApiArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                TopHeadlinesPagingSource(networkService)
            }
        ).flow
    }
}