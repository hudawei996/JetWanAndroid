package com.kuky.demo.wan.android.entity

import com.kk.android.jetwanandroid.data.HomeArticleDetail


/**
 * @author kuky.
 * @description
 */
data class ArticleData(
    val curPage: Int,
    val datas: MutableList<HomeArticleDetail>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)