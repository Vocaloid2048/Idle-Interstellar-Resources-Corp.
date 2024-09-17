package com.voc.idle.irc.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.unit.Dp
import files.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.pow
import kotlin.math.roundToInt

class UtilTools {

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalResourceApi::class)
    fun getLostImgByteArray() : ByteArray {
        return runBlocking {
            val job = async(Dispatchers.IO) {
                val assetByte: ByteArray = Res.readBytes("files/ico_lost_img.webp")
                return@async (assetByte)
            }
            job.await()
            job.getCompleted()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalResourceApi::class)
            /**
             * You must know the basic structure it is, JsonArray or JsonObject
             * E.g. getAssetsJsonByContext("character_data/character_list.json").jsonArray[0]
             */
    fun getAssetsJsonByFilePath(filePath: String): JsonElement {
        return runBlocking {
            val job = async(Dispatchers.Default) {
                try {
                    return@async Json.parseToJsonElement(getAssetsStringByFilePath(filePath))
                } catch (e: Exception) {
                    // Handle the exception, ErrorLogExporter Please!
                    errorLogExport("UtilTools","getAssetsWebpByFileName()",e)
                    return@async Json.parseToJsonElement("{}")
                }
            }
            job.await()
            job.getCompleted()
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalResourceApi::class)
    inline
            /**
             * You must know the basic structure it is, JsonArray or JsonObject
             * E.g. getAssetsJsonByContext("character_data/character_list.json").jsonArray[0]
             */
    fun <reified T> getAssetsJsonByFilePathWithIgnore(filePath: String): T {
        return runBlocking {
            val job = async(Dispatchers.Default) {
                try {
                    return@async Json {ignoreUnknownKeys = true} .decodeFromString<T>(getAssetsStringByFilePath(filePath))
                } catch (e: Exception) {
                    // Handle the exception, ErrorLogExporter Please!
                    errorLogExport("UtilTools","getAssetsJsonByFilePathWithIgnore()",e)
                    return@async Json {ignoreUnknownKeys = true} .decodeFromString<T>("{}")
                }
            }

            job.await()
            job.getCompleted()
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    fun getAssetsStringByFilePath(filePath: String): String{
        return runBlocking {
            val job = async(Dispatchers.IO) {
                try {
                    //Ararar I spent 4hrs on there lol
                    val assetString: String = Res.readBytes("files/data/${filePath}").decodeToString()
                    return@async assetString
                } catch (e: Exception) {
                    // Handle the exception, ErrorLogExporter Please!
                    errorLogExport("UtilTools","getAssetsWebpByFileName()",e)
                    return@async "{}"
                }
            }
            job.await()
            job.getCompleted()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalResourceApi::class)
    fun getAssetsJsonStrByFilePath(filePath: String): String {
        return runBlocking {
            val job = async(Dispatchers.Default) {
                try {
                    //Ararar I spent 4hrs on there lol
                    val assetString: String = Res.readBytes("files/data/${filePath}").decodeToString()
                    return@async assetString
                } catch (e: Exception) {
                    // Handle the exception, ErrorLogExporter Please!
                    errorLogExport("UtilTools","getAssetsWebpByFileName()",e)
                    return@async "{}"
                }
            }
            job.await()
            job.getCompleted()
        }
    }

    fun htmlDescApplier(htmlText: String, levelDataParams: ArrayList<Float>) : String{
        var htmlTextFinal = htmlText

        //Apply all params
        for((index, param) in levelDataParams.withIndex()){
            htmlTextFinal = htmlTextFinal
                .replace("#${(index+1)}[i]%","${UtilTools().formatDecimal((param * 100).toInt(),0)}%" )
                .replace("#${(index+1)}[f1]%","${UtilTools().formatDecimal(param * 100)}%" )
                .replace("#${(index+1)}[i]", UtilTools().formatDecimal(param.toInt(),0))
                .replace("#${(index+1)}[f1]", UtilTools().formatDecimal(param))
        }

        //Imaginary Color Modify
        htmlTextFinal = htmlTextFinal.replace("#F4D258","#D9A800")

        //Sparkle Chinese Name Modify
        htmlTextFinal = htmlTextFinal.replace("花<span style=\"color:#F84F36;\">火</span>","花火")

        return htmlDescApplierImpl(htmlTextFinal)
    }

    fun htmlDescApplierImpl(htmlText: String) : String{
        var htmlTextFinal = htmlText

        //Imaginary Color Modify
        htmlTextFinal = htmlTextFinal.replace("#F4D258","#D9A800")

        //Sparkle Chinese Name Modify
        htmlTextFinal = htmlTextFinal.replace("花<span style=\"color:#F84F36;\">火</span>","花火")

        return htmlTextFinal
    }

    @Composable
    fun removeStringResDoubleQuotes(stringResource: StringResource) : String{
        return stringResource(stringResource).removePrefix("\"").removeSuffix("\"")
    }

    fun pxToDp(px : Int, density: Float) : Dp {
        return Dp(px / density)
    }
    fun DpToPx(dp : Dp, density: Float) : Int {
        return (dp.value * density).roundToInt()
    }

    /**
     * Function that use for handling Dec's Format
     */
    fun formatDecimal(number: Number, decimalPlaces: Int = 1, isRoundDown: Boolean = false, isUnited: Boolean = false): String {
        val multiplier = 10.0.pow(decimalPlaces)
        val roundedNumber = if (isRoundDown) {
            kotlin.math.floor(number.toDouble() * multiplier)
        } else {
            kotlin.math.round(number.toDouble() * multiplier)
        } / multiplier

        val suffix = when {
            roundedNumber >= 1_000_000_000_000 -> "T"
            roundedNumber >= 1_000_000_000 -> "B"
            roundedNumber >= 1_000_000 -> "M"
            roundedNumber >= 1_000 -> "K"
            else -> ""
        }

        val scaledNumber = when (suffix) {
            "T" -> roundedNumber / 1_000_000_000_000
            "B" -> roundedNumber / 1_000_000_000
            "M" -> roundedNumber / 1_000_000
            "K" -> roundedNumber / 1_000
            else -> roundedNumber
        }

        val parts = if(isUnited){scaledNumber}else{roundedNumber}.toString().split('.')
        val integerPart = parts[0].reversed().chunked(3).joinToString(",").reversed()
        val decimalPart = parts.getOrNull(1)?.padEnd(decimalPlaces, '0') ?: "0".repeat(decimalPlaces)
        return if (isUnited) {
            "$integerPart${if (decimalPlaces > 0) {".$decimalPart"} else {""}}$suffix"
        } else {
            "$integerPart${if (decimalPlaces > 0) {".$decimalPart"} else {""}}"
        }
    }


    fun <T> swapItemsInArray(array: ArrayList<T>, fromIndex: Int, toIndex: Int) {
        val temp = array[fromIndex]
        array[fromIndex] = array[toIndex]
        array[toIndex] = temp
    }
}

val JsonArraySaver: Saver<JsonArray, Any> = listSaver(
    save = { listOf(it.toString()) },
    restore = { Json.parseToJsonElement(it[0]).jsonArray }
)
val JsonElementSaver: Saver<JsonElement, Any> = listSaver(
    save = { listOf(it.toString()) },
    restore = { Json.parseToJsonElement(it[0]) }
)

@Composable
fun String.replaceStrRes(newValue: String, index : Int = 1) : String{
    return this.replace("$"+"{$index}", newValue)
}

val BooleanSaver: Saver<Boolean, Any> = autoSaver()

fun Boolean.toInt() = if (this) 1 else 0
fun Int.toBoolean() = this != 0