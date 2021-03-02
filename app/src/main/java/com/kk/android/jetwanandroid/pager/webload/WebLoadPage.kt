package com.kk.android.jetwanandroid.pager.webload

import android.annotation.SuppressLint
import android.view.View
import android.webkit.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.kk.android.jetwanandroid.R

/**
 * @author kuky.
 * @description
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebLoadPage(navController: NavController, url: String?) {
    var visibleState by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }, title = {
                Text(
                    stringResource(R.string.art_detail),
                    fontSize = 18.sp
                )
            })
        },
    ) {
        Box(contentAlignment = Alignment.Center) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    WebView(ctx).apply {
                        overScrollMode = View.OVER_SCROLL_NEVER

                        settings.run {
                            javaScriptEnabled = true
                            javaScriptCanOpenWindowsAutomatically = true
                            allowFileAccess = true
                            layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                            useWideViewPort = true
                            loadWithOverviewMode = true
                            setSupportMultipleWindows(true)
                            setGeolocationEnabled(true)
                            setSupportZoom(true)
                            builtInZoomControls = true
                            displayZoomControls = false
                            domStorageEnabled = true
                            cacheMode = WebSettings.LOAD_NO_CACHE
                        }

                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                                if (url.isNullOrBlank()) return false

                                if (!url.matches(Regex("(http|https)?://(\\S)+"))) {
                                    return true
                                }

                                view?.loadUrl(url)
                                return true
                            }

                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                val overrideUrl = request?.url.toString()

                                if (overrideUrl.isBlank()) return false

                                if (!overrideUrl.matches(Regex("(http|https)?://(\\S)+"))) {
                                    return true
                                }

                                view?.loadUrl(overrideUrl)
                                return true
                            }
                        }

                        webChromeClient = object : WebChromeClient() {
                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                super.onProgressChanged(view, newProgress)
                                if (newProgress >= 90) visibleState = false
                            }
                        }

                        url?.let { path -> loadUrl(path) }
                    }
                }
            )

            if (visibleState) {
                Surface(
                    modifier = Modifier.clip(CircleShape).background(Color.Transparent)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.size(70.dp))
                        Text(stringResource(R.string.on_loading), fontSize = 12.sp, color = MaterialTheme.colors.primary)
                    }
                }
            }
        }
    }
}