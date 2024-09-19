package com.voc.idle.irc.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.voc.idle.irc.components.HeaderData
import com.voc.idle.irc.components.defaultHeaderData
import com.voc.idle.irc.utils.Preferences
import com.voc.idle.irc.utils.Screen
import com.voc.idle.irc.utils.navigateLimited
import files.Res
import files.interstellar_resource_corp_logo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource


@Composable
fun SplashPage(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    headerData: HeaderData = defaultHeaderData,
) {

    //Language().setAppLanguage()
    val showPopup = remember { mutableStateOf(!Preferences().AppSettings.isLangInitialized()) }

    val hasRefreshed = remember { mutableStateOf(false) }
    LaunchedEffect(showPopup.value) {

        CoroutineScope(Dispatchers.Default).launch {
            if (!hasRefreshed.value) {
                //async { functions() }.await()
            }

            hasRefreshed.value = true

            withContext(Dispatchers.Main) {
                //if (!showPopup.value) {
                    navController.navigateLimited(Screen.HomePage.route)
                //}
            }
        }
    }

    Box(modifier = Modifier.background(Color.Black).fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.interstellar_resource_corp_logo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(0.8f).aspectRatio(1f).align(Alignment.Center)
        )
    }


}