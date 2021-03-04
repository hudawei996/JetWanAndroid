package com.kk.android.jetwanandroid.pager.project

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kuky.demo.wan.android.entity.ProjectDetailData

/**
 * @author kuky.
 * @description
 */
class ProjectPagingDataSource(
    private val repository: ProjectRepository,
    private val pid: Int
) : PagingSource<Int, ProjectDetailData>() {

    override fun getRefreshKey(state: PagingState<Int, ProjectDetailData>) = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProjectDetailData> {
        val page = params.key ?: 1
        return try {
            val projects = repository.loadProjectByCategory(page, pid)

            LoadResult.Page(
                data = projects,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (projects.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}