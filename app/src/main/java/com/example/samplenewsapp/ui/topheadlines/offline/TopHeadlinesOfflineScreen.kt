package com.example.samplenewsapp.ui.topheadlines.offline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.samplenewsapp.data.local.entity.Article
import com.example.samplenewsapp.data.local.entity.toArticleModel
import com.example.samplenewsapp.ui.base.ArticleList
import com.example.samplenewsapp.ui.base.ShowError
import com.example.samplenewsapp.ui.base.ShowLoading
import com.example.samplenewsapp.ui.base.UiState
import com.example.samplenewsapp.ui.base.topAppBar
import com.example.samplenewsapp.utils.Constants.TOP_HEADLINES_OFFLINE

@Composable
fun TopHeadlinesOfflineRoute(
    onNewsClick: (url: String) -> Unit,
    viewModel: TopHeadlinesOfflineViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            topAppBar(
                title = TOP_HEADLINES_OFFLINE, showBackArrow = true
            ) { navController.popBackStack() }
        },
        content = { padding ->
            TopHeadlinesOfflineScreen(padding, uiState, onNewsClick, viewModel)
        }
    )

}

@Composable
fun TopHeadlinesOfflineScreen(
    padding: PaddingValues,
    uiState: UiState<List<Article>>,
    onNewsClick: (url: String) -> Unit,
    viewModel: TopHeadlinesOfflineViewModel
) {
    Column(modifier = Modifier.padding(padding)) {
        when (uiState) {
            is UiState.Success -> {
                val apiArticleList = uiState.data.map { it.toArticleModel() }
                ArticleList(apiArticleList, onNewsClick)
            }

            is UiState.Loading -> {
                ShowLoading()
            }

            is UiState.Error -> {
                ShowError(
                    text = uiState.message,
                    retryEnable = true
                ) {
                    viewModel.fetchNews()
                }
            }
        }
    }
}