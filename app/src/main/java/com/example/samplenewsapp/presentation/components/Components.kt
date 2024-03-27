package com.example.samplenewsapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.samplenewsapp.R
import com.example.samplenewsapp.data.di.module.toArticleDetail
import com.example.samplenewsapp.domain.model.detail.ArticleDetail
import com.example.samplenewsapp.domain.model.list.Article

@Composable
fun ArticleItem(article: Article, onClick:(String)->Unit) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Card (
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .padding(15.dp)
                .testTag("articleItem")
                .wrapContentHeight()
                .fillMaxWidth(0.8f)
                .clickable {
                    onClick.invoke(article.id)
                }
        ){
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                AsyncImage(model = article.imageUrl,
                    placeholder = painterResource(id = R.drawable.def),
                    contentDescription =null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .testTag("articleImage")
                    , contentScale = ContentScale.None)

                Text(text = article.title, modifier = Modifier
                    .padding(8.dp)
                    .testTag("articleTitle"), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}