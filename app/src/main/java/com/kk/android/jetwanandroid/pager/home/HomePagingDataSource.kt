package com.kk.android.jetwanandroid.pager.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kk.android.jetwanandroid.data.HomeArticleDetail

/**
 * @author kuky.
 * @description
 */

class HomePagingDataSource(
    private val repository: HomeRepository
) : PagingSource<Int, HomeArticleDetail>() {

    override fun getRefreshKey(state: PagingState<Int, HomeArticleDetail>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeArticleDetail> {
        val page = params.key ?: 0
        return try {
            val articles = if (page == 0) {
                mutableListOf<HomeArticleDetail>().apply {
                    addAll(repository.loadTops())
                    addAll(repository.loadPageData(page))
                }
            } else {
                repository.loadPageData(page)
            }

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}