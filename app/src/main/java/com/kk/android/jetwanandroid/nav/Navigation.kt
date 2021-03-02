package com.kk.android.jetwanandroid.nav

import androidx.navigation.NavController
import androidx.navigation.compose.navigate

/**
 * @author kuky.
 * @description
 */

object NavDestinations {
    const val Main = "Main"
    const val WebLoad = "WebLoad"
}

class NavActions(private val navController: NavController) {

    fun loadWebInfo(url: String) = navController.navigate("${NavDestinations.WebLoad}?url=$url")

    val backStack: () -> Unit = { navController.popBackStack() }
}