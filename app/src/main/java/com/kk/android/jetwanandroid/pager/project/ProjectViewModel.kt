package com.kk.android.jetwanandroid.pager.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import com.kk.android.jetwanandroid.utils.constPagerConfig
import com.kuky.demo.wan.android.entity.ProjectCategoryData
import com.kuky.demo.wan.android.entity.ProjectDetailData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * @author kuky.
 * @description
 */
class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {

    val selectedCategoryId = MutableStateFlow(0)
    val categories = MutableStateFlow(mutableListOf<ProjectCategoryData>())
    var projectsPager: Pager<Int, ProjectDetailData>? = null

    fun fetchCategories() {
        viewModelScope.launch {
            categories.value = repository.loadCategories()
                .apply { selectedCategoryId.value = first().id }
        }
    }

    fun changeSelectedCategory(id: Int): Pager<Int, ProjectDetailData> {
        selectedCategoryId.value = id
        return Pager(constPagerConfig) {
            ProjectPagingDataSource(repository, id)
        }.also { projectsPager = it }
    }
}

class ProjectViewModelFactory(private val repository: ProjectRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectViewModel(repository) as T
    }
}

