package com.voc.idle.irc.utils

import com.russhwolf.settings.Settings

class Preferences {
    val AppSettings = AppSettingsClass()

    private class Constants {
        val CHAR_LIST_UPDATE_MINS = 15
        val CHAR_WEIGHT_LIST_UPDATE_MINS = 15
        val LEADERBOARD_UPDATE_MINS = 60
        val KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME = "lastUpdateHoyolabCharListTime"
        val KEY_HYB_LEADERBOARD_LAST_UPDATE_TIME = "lastUpdateHoyolabLeaderboardTime"
    }

    class AppSettingsClass(){
        fun isLangInitialized(): Boolean {
            return Settings().getBoolean("isLangInitialized", false)
        }
        fun setLangInitialized(){
            Settings().putBoolean("isLangInitialized", true)
        }
    }
}