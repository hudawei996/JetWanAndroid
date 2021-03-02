package com.kk.android.jetwanandroid

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.kk.android.jetwanandroid.nav.NavDestinations
import com.kk.android.jetwanandroid.pager.main.MainPage
import com.kk.android.jetwanandroid.pager.webload.WebLoadPage

/**
 * @author kuky.
 * @description
 */

@Composable
fun WanScreen() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavDestinations.Main) {
        composable(NavDestinations.Main) { MainPage(navController) }
        composable(
            "${NavDestinations.WebLoad}?url={url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            WebLoadPage(navController, url = backStackEntry.arguments?.getString("url"))
        }
    }
}