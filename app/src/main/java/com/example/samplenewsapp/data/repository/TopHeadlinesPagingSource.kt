package com.example.samplenewsapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.samplenewsapp.data.api.NetworkService
import com.example.samplenewsapp.data.model.ApiArticle
import com.example.samplenewsapp.utils.Constants.DEFAULT_COUNTRY
import com.example.samplenewsapp.utils.Constants.INITIAL_PAGE
import com.example.samplenewsapp.utils.Constants.PAGE_SIZE

class TopHeadlinesPagingSource(
    private val networkService: NetworkService
) : PagingSource<Int, ApiArticle>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiArticle> {
        return try {
            val page = params.key ?: INITIAL_PAGE

            val response = networkService.getTopHeadlinesPaging(
                country = DEFAULT_COUNTRY,
                page = page,
                pageSize = PAGE_SIZE
            )

            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == INITIAL_PAGE) null else page.minus(1),
                nextKey = if (response.articles.isEmpty()) null else page.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ApiArticle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}