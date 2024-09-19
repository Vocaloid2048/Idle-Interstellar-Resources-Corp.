/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package com.voc.idle.irc.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterState
import com.dokar.sonner.rememberToasterState
import com.russhwolf.settings.Settings
import com.voc.idle.irc.components.HeaderData
import com.voc.idle.irc.components.defaultHeaderData
import com.voc.idle.irc.screens.HomePageScreen
import com.voc.idle.irc.screens.SplashPage
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import kotlinx.datetime.Clock

//This should not be there, but CharacterCard need it in clickable, without using @Composable ...
lateinit var navControllerInstance : NavController
lateinit var toastInstance : ToasterState

var isHomePaged = false
sealed class Screen(val route: String, val headerData: HeaderData = defaultHeaderData) {
    data object SplashPage : Screen("SplashPage", HeaderData())

    data object HomePage : Screen("HomePage", HeaderData())

}

@Composable
fun Navigation() {
    println("HI IS FROM APP.KT")
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    navControllerInstance = navController
    toastInstance = rememberToasterState()

    NavHost(navController = navController, startDestination = Screen.SplashPage.route) {
        composable(Screen.SplashPage.route) {
            RootContent(
                screen = Screen.SplashPage,
                snackbarHostState = snackbarHostState
            ) {
                SplashPage(navController = navController, headerData = Screen.SplashPage.headerData)
            }
        }

        composable(Screen.HomePage.route) {
            RootContent(
                screen = Screen.HomePage,
                snackbarHostState = snackbarHostState
            ) {
                Box {
                    HomePageScreen(navController = navController, headerData = Screen.HomePage.headerData)
                }
            }
        }
    }

}

fun NavController.navigateLimited(route: String, options: NavOptions? = null) {
    val navigationInterval: Long = 1000 // 1 second
    val lastNavigationTime: Long = Settings().getLong("lastNavigationTime", 0)

    val currentTime = Clock.System.now().toEpochMilliseconds()
    if (currentTime - lastNavigationTime >= navigationInterval) {
        Settings().putLong("lastNavigationTime", currentTime)
        navigate(route, options)
    }
}

@Composable
fun RootContent(
    screen: Screen,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
    page: @Composable () -> Unit
) {
    println("I'm going to show ${screen.route}")
    val hazeStateRoot = remember { HazeState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState!!)
        },
    ) {
        //MakeBackground(screen = screen)
        //Landscape can do ... ?
        Box(
            modifier = Modifier.haze(hazeStateRoot)
        ) {
            page()
        }

        Toaster(
            state = toastInstance,
            richColors = true,
            maxVisibleToasts = 10,
            alignment = Alignment.BottomCenter,
            showCloseButton = true,
            darkTheme = true,
        )
    }
}
