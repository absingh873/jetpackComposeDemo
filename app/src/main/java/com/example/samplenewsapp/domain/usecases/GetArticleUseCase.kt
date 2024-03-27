package com.example.samplenewsapp.domain.usecases

import com.example.samplenewsapp.common.Constants
import com.example.samplenewsapp.common.Resource
import com.example.samplenewsapp.domain.model.list.Article
import com.example.samplenewsapp.domain.repository.GetArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(private val getArticleRepository: GetArticleRepository) {

    operator fun invoke(): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = getArticleRepository.getArticle(Constants.ARTICLE_LISTAPIKEY)))
        } catch (e : Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}