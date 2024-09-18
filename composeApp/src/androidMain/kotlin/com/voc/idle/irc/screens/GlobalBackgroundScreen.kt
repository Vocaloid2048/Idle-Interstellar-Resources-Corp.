/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package screens

import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.voc.idle.irc.R
import com.voc.idle.irc.utils.Screen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import org.jetbrains.compose.resources.ExperimentalResourceApi
import utils.Black
import utils.BlackAlpha20
import utils.BlackAlpha80
import utils.IRCTheme
import utils.Transparent


val gradient = Brush.verticalGradient(
    colors = listOf(
        BlackAlpha80,
        BlackAlpha20
    )
)
val gradientBottom = Brush.verticalGradient(
    colors = listOf(
        Transparent,
        Black
    )
)

lateinit var backgroundScreenHazeState : HazeState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MakeBackground(modifier: Modifier = Modifier, screen: Screen = Screen.HomePage) {
    backgroundScreenHazeState = remember { HazeState() }
    var isBlur = true;
    var isGradient = true;

    Box(
        Modifier.haze(backgroundScreenHazeState)
    ){
        Image(
            painter = androidx.compose.ui.res.painterResource(R.drawable.bg_universe_tim_bernhard),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().blur(if (isBlur) 0.dp else 0.dp)
        )
        Box(
            modifier = Modifier.matchParentSize().background(
                if (isGradient) gradientBottom else Brush.linearGradient(listOf(Color.Transparent,Color.Transparent))
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GlobalBackgroundPreview() {

    IRCTheme {
        MakeBackground(screen = Screen.HomePage)
    }
}