package com.voc.idle.irc

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Idle Interstellar Resources Corp",
    ) {
        App()
    }
}