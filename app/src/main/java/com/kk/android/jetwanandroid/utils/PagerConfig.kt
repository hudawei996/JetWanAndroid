package com.kk.android.jetwanandroid.utils

import androidx.paging.PagingConfig

/**
 * @author kuky.
 * @description
 */
private const val PAGING_PAGER_SIZE = 20

val constPagerConfig = PagingConfig(
    pageSize = PAGING_PAGER_SIZE,
    prefetchDistance = PAGING_PAGER_SIZE / 2,
    initialLoadSize = PAGING_PAGER_SIZE
)