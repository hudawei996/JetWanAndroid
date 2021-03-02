package com.kk.android.jetwanandroid.pager.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.kk.android.jetwanandroid.nav.NavActions
import com.kk.android.jetwanandroid.network.createService
import com.kk.android.jetwanandroid.utils.renderHtml

/**
 * @author kuky.
 * @description
 */
@Composable
fun HomePage(navController: NavController) {
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(HomeRepository(createService()))
    )

    val lazyPagingItems = viewModel.getHomeArticles().collectAsLazyPagingItems()

    LazyColumn {
        items(lazyPagingItems = lazyPagingItems) { article ->
            if (article != null) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .clickable { NavActions(navController).loadWebInfo(article.link) },
                    shape = MaterialTheme.shapes.large, elevation = 4.dp
                ) {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        val (arcTitle, arcChapter, arcTime) = createRefs()

                        Text(
                            article.title.renderHtml(), color = Color.Black, fontSize = 16.sp, maxLines = 2,
                            fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth().constrainAs(arcTitle) { top.linkTo(parent.top) }
                        )

                        Text(
                            "${article.superChapterName}/${article.chapterName}", fontSize = 14.sp, maxLines = 1,
                            modifier = Modifier.constrainAs(arcChapter) {
                                top.linkTo(arcTitle.bottom, margin = 20.dp)
                                end.linkTo(parent.end)
                            }
                        )

                        Text(
                            "${article.author}\t\t${article.niceDate}", fontSize = 14.sp, maxLines = 1,
                            modifier = Modifier.constrainAs(arcTime) {
                                top.linkTo(arcChapter.bottom, margin = 4.dp)
                                end.linkTo(parent.end)
                            }
                        )
                    }
                }
            }
        }
    }
}