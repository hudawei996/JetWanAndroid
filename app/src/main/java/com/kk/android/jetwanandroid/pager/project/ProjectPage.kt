package com.kk.android.jetwanandroid.pager.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.kk.android.jetwanandroid.commonui.*
import com.kk.android.jetwanandroid.nav.NavActions
import com.kk.android.jetwanandroid.network.createService
import com.kk.android.jetwanandroid.utils.renderHtml
import com.kuky.demo.wan.android.entity.ProjectCategoryData
import com.kuky.demo.wan.android.entity.ProjectDetailData

//import dev.chrisbanes.accompanist.coil.CoilImage

/**
 * @author kuky.
 * @description
 */
@Composable
fun ProjectPage(navController: NavController, lazyListController: (LazyListState) -> Unit = {}) {
    val viewModel: ProjectViewModel = viewModel(
        factory = ProjectViewModelFactory(ProjectRepository(createService()))
    )

    val lazyListState = rememberLazyListState().apply(lazyListController)
    var refreshState by remember { mutableStateOf(false) }

    val selectedCategoryId = viewModel.selectedCategoryId.collectAsState()
    val categories = viewModel.categories.collectAsState()

    if (categories.value.isEmpty()) {
        viewModel.fetchCategories()
    }

    var projectPager by remember {
        mutableStateOf(
            viewModel.changeSelectedCategory(selectedCategoryId.value)
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(categories.value, key = { it.id }) { category ->
                CategoryLabel(category, category.id == selectedCategoryId.value) {
                    projectPager = viewModel.changeSelectedCategory(category.id)
                }
            }
        }

        Divider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.primary)

        val projects = projectPager.flow.collectAsLazyPagingItems().apply {
            refreshState = (loadState.refresh is LoadState.Loading)
        }

        SwipeToRefreshLayout(refreshState, modifier = Modifier.fillMaxSize(),
            // TODO: 2021/3/4 projects.refresh() not work if replace pager
            onRefresh = { projects.refresh() }) {
            projects.apply {
                if (loadState.refresh is LoadState.Error) {
                    LoadErrorView { refresh() }
                } else if (loadState.refresh is LoadState.NotLoading && itemCount == 0) {
                    LoadEmptyView()
                } else {
                    LazyColumn(state = lazyListState) {
                        items(lazyPagingItems = projects) { project ->
                            if (project != null) {
                                CategoryView(navController, project)
                            }
                        }

                        when (loadState.append) {
                            is LoadState.Error -> item { OnLoadingMoreErrorView { retry() } }

                            is LoadState.Loading -> item { OnLoadingMoreView() }

                            is LoadState.NotLoading ->
                                if (loadState.refresh !is LoadState.Loading) {
                                    item { NoMoreDataView() }
                                }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryView(navController: NavController, project: ProjectDetailData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { NavActions(navController).loadWebInfo(project.link) },
        shape = MaterialTheme.shapes.large, elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        project.title.renderHtml(), color = Color.Black, fontSize = 16.sp, maxLines = 2,
                        fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        project.desc.renderHtml(), fontSize = 14.sp, maxLines = 3, overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start, modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    )
                }

                val painter = rememberCoilPainter(request = project.envelopePic, fadeIn = true)
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .heightIn(60.dp, 100.dp)
                ) {
                    Image(painter = painter, contentDescription = null)

                    when (painter.loadState) {
                        is ImageLoadState.Loading, is ImageLoadState.Error -> ImagePlaceHolder()
                    }
                }
            }

            Text(
                "${project.author}\t\t${project.niceDate}", fontSize = 14.sp, maxLines = 1,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp)
                    .clip(MaterialTheme.shapes.large)
            )
        }
    }
}

@Composable
fun ImagePlaceHolder() {
    Box(
        Modifier
            .width(60.dp)
            .height(100.dp)
            .background(Color.Gray)
            .clip(MaterialTheme.shapes.large)
    )
}

@Composable
fun CategoryLabel(
    category: ProjectCategoryData, isSelected: Boolean,
    onSelectedClick: () -> Unit
) {
    val normalModifier = Modifier
        .padding(4.dp)
        .background(
            if (isSelected) MaterialTheme.colors.primary else Color.White,
            shape = RoundedCornerShape(4.dp)
        )
        .padding(horizontal = 8.dp, vertical = 4.dp)

    val clickModifier = normalModifier.clickable { onSelectedClick() }

    Text(
        category.name.renderHtml(), fontSize = 14.sp,
        color = if (isSelected) Color.White else MaterialTheme.colors.primary,
        modifier = if (isSelected) normalModifier else clickModifier
    )
}


//region Category View By ConstraintLayout, Layout not work well
@Composable
fun CategoryViewByConstraint(navController: NavController, project: ProjectDetailData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { NavActions(navController).loadWebInfo(project.link) },
        shape = MaterialTheme.shapes.large, elevation = 4.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val (proTitle, proDesc, proImg, proAuthor) = createRefs()

            Image(
                painter = rememberCoilPainter(request = project.envelopePic, fadeIn = true,
                    requestBuilder = { transformations(CircleCropTransformation()) }),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .constrainAs(proImg) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
            )

//            CoilImage(data = project.envelopePic, contentDescription = null, fadeIn = true,
//                modifier = Modifier.size(60.dp).constrainAs(proImg) {
//                    end.linkTo(parent.end)
//                    top.linkTo(parent.top)
//                })

            Text(
                project.title.renderHtml(), color = Color.Black, fontSize = 16.sp, maxLines = 2,
                fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Start,
                modifier = Modifier
                    .constrainAs(proTitle) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(proImg.start, margin = 4.dp)
                    }
                    .background(Color.Red)
                    .wrapContentWidth()
            )

            Text(
                project.desc.renderHtml(), fontSize = 14.sp, maxLines = 2, overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start, modifier = Modifier
                    .constrainAs(proDesc) {
                        start.linkTo(proTitle.start)
                        top.linkTo(proTitle.bottom, margin = 16.dp)
                        end.linkTo(proTitle.end)
                    }
                    .wrapContentWidth()
            )

            Text("${project.author}\t\t${project.niceDate}", fontSize = 14.sp, maxLines = 1,
                modifier = Modifier.constrainAs(proAuthor) {
                    top.linkTo(proDesc.bottom, margin = 8.dp)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}
//endregion