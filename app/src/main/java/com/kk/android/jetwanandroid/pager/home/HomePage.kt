package com.kk.android.jetwanandroid.pager.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.kk.android.jetwanandroid.R
import com.kk.android.jetwanandroid.commonui.*
import com.kk.android.jetwanandroid.data.HomeArticleDetail
import com.kk.android.jetwanandroid.nav.NavActions
import com.kk.android.jetwanandroid.network.createService
import com.kk.android.jetwanandroid.utils.renderHtml

/**
 * @author kuky.
 * @description
 */
@Composable
fun HomePage(navController: NavController, lazyListController: (LazyListState) -> Unit = {}) {
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(HomeRepository(createService()))
    )

    var refreshState by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState().apply { lazyListController(this) }

    val lazyPagingItems = viewModel.homeArticles.collectAsLazyPagingItems().apply {
        refreshState = (loadState.refresh is LoadState.Loading)
    }

    SwipeToRefreshLayout(refreshState, onRefresh = { lazyPagingItems.refresh() }) {
        lazyPagingItems.apply {
            if (loadState.refresh is LoadState.Error) { // when load first page errors occur load an retry image
                LoadErrorView { refresh() }
            } else if (loadState.refresh is LoadState.NotLoading && itemCount == 0) { // when there is no data load an empty image
                LoadEmptyView()
            } else {
                LazyColumn(state = lazyListState) {
                    items(lazyPagingItems = lazyPagingItems) { article ->
                        if (article != null) {
                            ArticleView(navController, article)
                        }
                    }

                    if (loadState.append is LoadState.Error) { // when load more errors occur
                        item { OnLoadingMoreErrorView { retry() } }
                    } else if (loadState.append is LoadState.Loading) { // when load more is loading state
                        item { OnLoadingMoreView() }
                    }
                }
            }
        }
    }
}

// View to load Article Information
@Composable
fun ArticleView(navController: NavController, article: HomeArticleDetail) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { NavActions(navController).loadWebInfo(article.link) },
        shape = MaterialTheme.shapes.large, elevation = 4.dp
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            val (arcTitle, arcChapter, arcTime, hotLabel, freshLabel) = createRefs()

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

            if (article.type == 1) {
                Image(
                    painter = painterResource(R.drawable.ic_hot),
                    contentDescription = null,
                    modifier = Modifier.constrainAs(hotLabel) {
                        bottom.linkTo(arcTime.bottom)
                        start.linkTo(parent.start)
                    }.size(20.dp)
                )
            }

            if (article.fresh) {
                Image(
                    painter = painterResource(R.drawable.ic_fresh),
                    contentDescription = null,
                    modifier = Modifier.constrainAs(freshLabel) {
                        bottom.linkTo(arcTime.bottom)
                        start.linkTo(hotLabel.end)
                    }.padding(start = 4.dp).size(20.dp)
                )
            }
        }
    }
}