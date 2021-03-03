package com.kk.android.jetwanandroid.pager.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.kk.android.jetwanandroid.utils.constPagerConfig

/**
 * @author kuky.
 * @description
 */
class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    val homeArticles = Pager(constPagerConfig) {
        HomePagingDataSource(repository)
    }.flow.cachedIn(viewModelScope)
}

class HomeViewModelFactory(
    private val repository: HomeRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}