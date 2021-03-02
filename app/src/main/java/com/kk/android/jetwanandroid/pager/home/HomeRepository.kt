package com.kk.android.jetwanandroid.pager.home

import com.kk.android.jetwanandroid.data.HomeArticleDetail
import com.kk.android.jetwanandroid.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author kuky.
 * @description
 */
class HomeRepository(private val api: ApiService) {

    suspend fun loadPageData(page: Int): MutableList<HomeArticleDetail> =
        withContext(Dispatchers.IO) {
            api.homeArticles(page).data?.datas ?: mutableListOf()
        }

    suspend fun loadTops(): MutableList<HomeArticleDetail> =
        withContext(Dispatchers.IO) {
            api.topArticle("").data ?: mutableListOf()
        }
}