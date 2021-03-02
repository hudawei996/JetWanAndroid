package com.kk.android.jetwanandroid.utils

import android.os.Build
import android.text.Html
import androidx.core.text.HtmlCompat

/**
 * @author kuky.
 * @description
 */
@Suppress("DEPRECATION")
fun String.renderHtml(): String =
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
        Html.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
    else Html.fromHtml(this).toString()