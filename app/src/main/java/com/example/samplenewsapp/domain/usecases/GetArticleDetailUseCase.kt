package com.example.samplenewsapp.domain.usecases

import com.example.samplenewsapp.common.Resource
import com.example.samplenewsapp.domain.model.detail.ArticleDetail
import com.example.samplenewsapp.domain.repository.GetArticleDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetArticleDetailUseCase @Inject constructor(private val getArticleDetailRepository: GetArticleDetailRepository) {

    operator fun invoke(id:String) : Flow<Resource<ArticleDetail>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = getArticleDetailRepository.getArticleDetail(id)))
        } catch (e : Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }

}