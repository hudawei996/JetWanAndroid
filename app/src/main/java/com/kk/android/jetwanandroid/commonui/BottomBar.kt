package com.kk.android.jetwanandroid.commonui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author kuky.
 * @description
 */
@Composable
fun HomeBottomItem(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @DrawableRes drawable: Int,
    color: Color
) {
    Column(
        modifier.padding(horizontal = 0.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(drawable),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = color
        )

        Text(
            text = stringResource(title),
            fontSize = 12.sp, color = color
        )
    }
}

@Composable
fun HomeBottomBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier.fillMaxWidth(),
        content = content
    )
}