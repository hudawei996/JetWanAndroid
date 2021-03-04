package com.kk.android.jetwanandroid.pager.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kk.android.jetwanandroid.R
import com.kk.android.jetwanandroid.commonui.HomeBottomItem
import com.kk.android.jetwanandroid.commonui.Pager
import com.kk.android.jetwanandroid.commonui.PagerState
import com.kk.android.jetwanandroid.pager.home.HomePage
import com.kk.android.jetwanandroid.pager.project.ProjectPage
import com.kk.android.jetwanandroid.pager.share.SharePage
import com.kk.android.jetwanandroid.pager.system.SystemPage
import kotlinx.coroutines.launch

/**
 * @author kuky.
 * @description
 */
@Composable
fun MainPage(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    var mLazyListState: LazyListState? = null

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        drawerShape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
        topBar = {
            TopAppBar(elevation = 1.dp,
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 18.sp,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch { scaffoldState.drawerState.open() }
                        },
                        content = {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        })
                },
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = {
                        coroutineScope.launch {
                            mLazyListState?.scrollToItem(0)
                        }
                    })
                })
        },
        drawerContent = { HomeDrawer() }
    ) {
        val pagerState by remember { mutableStateOf(PagerState(maxPage = 3)) }

        Column(modifier = Modifier.fillMaxSize()) {
            Pager(pagerState, Modifier.weight(1f)) {
                when (page) {
                    0 -> HomePage(navController) { mLazyListState = it }
                    1 -> ProjectPage(navController){mLazyListState = it}
                    2 -> SystemPage()
                    3 -> SharePage()
                }
            }

            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary, thickness = 0.5f.dp
            )

            HomeNavBar(pagerState.currentPage) {
                pagerState.currentPage = it
            }
        }
    }
}

@Composable
fun HomeNavBar(current: Int, onSelectedPositionChange: (Int) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        HomeBottomItem(
            modifier = Modifier.weight(1f)
                .clickable { onSelectedPositionChange(0) },
            title = R.string.home,
            drawable = R.drawable.ic_nav_home,
            color = if (current == 0) MaterialTheme.colors.primary else Color.Black
        )

        HomeBottomItem(
            modifier = Modifier.weight(1f)
                .clickable { onSelectedPositionChange(1) },
            title = R.string.projects,
            drawable = R.drawable.ic_nav_project,
            color = if (current == 1) MaterialTheme.colors.primary else Color.Black
        )

        HomeBottomItem(
            modifier = Modifier.weight(1f)
                .clickable { onSelectedPositionChange(2) },
            title = R.string.system,
            drawable = R.drawable.ic_nav_system,
            color = if (current == 2) MaterialTheme.colors.primary else Color.Black
        )

        HomeBottomItem(
            modifier = Modifier.weight(1f)
                .clickable { onSelectedPositionChange(3) },
            title = R.string.share,
            drawable = R.drawable.ic_nav_share,
            color = if (current == 3) MaterialTheme.colors.primary else Color.Black
        )
    }
}

@Composable
fun HomeDrawer() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

    }
}

