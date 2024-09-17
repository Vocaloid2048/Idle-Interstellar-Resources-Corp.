package com.voc.idle.irc

import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.voc.idle.irc.screens.SplashPage
import org.jetbrains.compose.ui.tooling.preview.Preview
import utils.Stargazer3Theme


@Composable
@Preview
fun App() {
    Stargazer3Theme {
        SplashPage(navController = rememberNavController())
    }
}