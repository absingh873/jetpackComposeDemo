package com.example.samplenewsapp.ui.newssources

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.samplenewsapp.data.model.ApiSource
import com.example.samplenewsapp.navigation.Screen
import com.example.samplenewsapp.ui.base.ShowError
import com.example.samplenewsapp.ui.base.ShowLoading
import com.example.samplenewsapp.ui.base.UiState
import com.example.samplenewsapp.ui.base.topAppBar
import com.example.samplenewsapp.utils.Constants


@Composable
fun NewsSourcesRoute(
    navController: NavController, viewModel: NewsSourcesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        topAppBar(
            title = Constants.NEWS_SOURCES, showBackArrow = true
        ) { navController.popBackStack() }
    }, content = { padding ->
        NewsSourcesScreen(padding, uiState) { sourceId ->
            navController.navigate(route = Screen.NewsBySources.passData(sourceId = sourceId))
        }
    })
}

@Composable
fun NewsSourcesScreen(
    padding: PaddingValues,
    uiState: UiState<List<ApiSource>>,
    onSourceClick: (source: String) -> Unit
) {
    Column(
        modifier = Modifier.padding(padding)
    ) {
        when (uiState) {
            is UiState.Success -> {
                SourceList(uiState.data, onSourceClick)
            }

            is UiState.Loading -> {
                ShowLoading()
            }

            is UiState.Error -> {
                ShowError(text = uiState.message)
            }
        }
    }
}

@Composable
fun SourceList(sources: List<ApiSource>, onSourceClick: (source: String) -> Unit) {
    LazyColumn {
        items(sources.size) { index ->
            Source(source = sources[index], onSourceClick = onSourceClick)
        }
    }
}

@Composable
fun Source(source: ApiSource, onSourceClick: (source: String) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { source.id?.let { onSourceClick(it) } }
            .padding(vertical = 23.dp, horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = source.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                fontSize = 22.sp
            )
        }

    }
}