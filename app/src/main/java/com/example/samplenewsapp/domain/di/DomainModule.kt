package com.example.samplenewsapp.domain.di

import com.example.samplenewsapp.data.network.NetworkService
import com.example.samplenewsapp.data.repository.ArticleDetailRepositoryImpl
import com.example.samplenewsapp.data.repository.ArticleReposetoryImpl
import com.example.samplenewsapp.domain.repository.GetArticleDetailRepository
import com.example.samplenewsapp.domain.repository.GetArticleRepository
import com.example.samplenewsapp.domain.usecases.GetArticleDetailUseCase
import com.example.samplenewsapp.domain.usecases.GetArticleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {
    @Provides
    fun providesGetArticleRepository(apiService: NetworkService) : GetArticleRepository{
        return ArticleReposetoryImpl(apiService)
    }

    @Provides
    fun providesGetArticleDetailsRepository(apiService: NetworkService):GetArticleDetailRepository{
        return ArticleDetailRepositoryImpl(apiService)
    }

    @Provides
    fun providesGetArticleUseCase(getArticleRepository: GetArticleRepository) : GetArticleUseCase{
        return GetArticleUseCase(getArticleRepository)
    }

    @Provides
    fun providesGetArticleDetailsUseCase (getArticleDetailsRepository: GetArticleDetailRepository) : GetArticleDetailUseCase{
        return GetArticleDetailUseCase(getArticleDetailsRepository)
    }
}