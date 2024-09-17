package types

import com.voc.idle.irc.BuildKonfig
import kotlinx.serialization.Serializable


@Serializable
data class AppInfo(
    val appProfile: String = "BETA",
    val appVersionName: String = "1.0.0",
    val appVersionCode: Int = 1,
){
    companion object{
        val AppInfoInstance = AppInfo(
            BuildKonfig.appProfile,
            BuildKonfig.appVersionName, BuildKonfig.appVersionCode)
    }
}