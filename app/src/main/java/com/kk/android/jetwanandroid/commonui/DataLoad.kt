package com.kk.android.jetwanandroid.commonui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kk.android.jetwanandroid.R

/**
 * @author kuky.
 * @description
 */
@Composable
fun LoadErrorView(modifier: Modifier = Modifier, reload: () -> Unit) {
    Box(
        modifier = modifier.fillMaxSize().clickable { reload() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(R.drawable.tag_load_error),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun LoadEmptyView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(R.drawable.tag_empty),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun NoMoreDataView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth().height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(R.string.no_more_data),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun OnLoadingMoreView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth().height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(R.string.on_loading),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun OnLoadingMoreErrorView(modifier: Modifier = Modifier, retryLoadMore: () -> Unit) {
    Box(
        modifier = modifier.fillMaxWidth().height(50.dp)
            .clickable { retryLoadMore() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(R.string.retry),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}