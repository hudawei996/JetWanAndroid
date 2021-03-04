package com.kk.android.jetwanandroid.pager.project

import com.kk.android.jetwanandroid.network.ApiService
import com.kuky.demo.wan.android.entity.ProjectCategoryData
import com.kuky.demo.wan.android.entity.ProjectDetailData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author kuky.
 * @description
 */
class ProjectRepository(private val api: ApiService) {
    suspend fun loadCategories(): MutableList<ProjectCategoryData> =
        withContext(Dispatchers.IO) {
            api.projectCategory().data ?: mutableListOf()
        }

    suspend fun loadProjectByCategory(page: Int, cid: Int): MutableList<ProjectDetailData> =
        withContext(Dispatchers.IO) {
            api.projectList(page, cid, "").data?.datas ?: mutableListOf()
        }
}