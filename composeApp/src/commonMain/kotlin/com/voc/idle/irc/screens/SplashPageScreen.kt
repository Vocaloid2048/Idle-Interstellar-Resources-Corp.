package com.voc.idle.irc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.voc.idle.irc.components.HeaderData
import com.voc.idle.irc.components.defaultHeaderData
import files.Res
import files.interstellar_resource_corp_logo
import org.jetbrains.compose.resources.painterResource
import utils.Black


@Composable
fun SplashPage(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    headerData: HeaderData = defaultHeaderData,
) {
    Box(modifier = Modifier.background(Black).fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.interstellar_resource_corp_logo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(0.8f).aspectRatio(1f).align(Alignment.Center)
        )
    }
}