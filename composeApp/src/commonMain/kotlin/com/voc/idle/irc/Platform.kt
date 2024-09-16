package com.voc.idle.irc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform