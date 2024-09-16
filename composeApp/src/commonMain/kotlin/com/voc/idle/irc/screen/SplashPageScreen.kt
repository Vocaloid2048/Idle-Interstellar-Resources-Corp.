package com.voc.idle.irc.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.NavigatorState
import androidx.navigation.compose.rememberNavController
import files.Res
import files.interstellar_resource_corp_icon
import files.interstellar_resource_corp_logo
import org.jetbrains.compose.resources.painterResource
import utils.AppFontSpecial
import utils.Black
import utils.FontSizeNormal12
import utils.FontSizeNormal14


@Composable
fun SplashPage(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
) {
    Box(modifier = Modifier.background(Black).fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.interstellar_resource_corp_logo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().aspectRatio(1f).align(Alignment.Center)
        )
    }
}