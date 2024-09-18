package com.voc.idle.irc.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.painterResource

lateinit var multiplyTimes: MutableState<MultiplyEnum>

enum class MultiplyEnum(val text:String, val value: Int) {
    ONE("x1",1), TEN("x10",10), HUNDRED("x100",100), MAX("MAX",-1)
}

//This is the Bar showing in the Redeem Page
//Which for user to choose to multiply the amount of item they want to redeem

@Composable
fun MultiplyButtonBar(
    modifier: Modifier = Modifier,
    hazeState: HazeState = remember { HazeState() },
) {
    multiplyTimes = remember { mutableStateOf(MultiplyEnum.ONE) }
    Box(modifier = Modifier.fillMaxWidth().clickable(indication = null, onClick = {}, interactionSource = remember { MutableInteractionSource() })) {
        Box(
            modifier = Modifier.align(Alignment.Center)
                .background(Color(0xCC222222), shape = RoundedCornerShape(25.dp))
                .border(
                    width = 2.dp,
                    color = Color(0xCC3C3C43),
                    shape = RoundedCornerShape(25.dp)
                ).hazeChild(
                    hazeState,
                    style = HazeStyle(Color.Unspecified, 20.dp, Float.MIN_VALUE),
                    shape = RoundedCornerShape(25.dp)
                )
        ) {
            Row(modifier = Modifier.padding(6.dp)) {
                for ((index, item) in MultiplyEnum.entries.withIndex()) {
                    val startPadding = if (index == 0) 0.dp else 4.dp
                    val endPadding = if (index == MultiplyEnum.entries.size) 0.dp else 4.dp
                    Box(
                        modifier = Modifier.padding(
                            start = startPadding,
                            end = endPadding
                        )
                            .height(30.dp).requiredWidth(48.dp).wrapContentWidth().background(
                                Color(if (multiplyTimes.value == item) 0x33FFFFFF else 0x00FFFFFF),
                                shape = RoundedCornerShape(25.dp)
                            ).clickable(
                                onClick = {
                                    multiplyTimes.value = item;
                                },
                                indication = rememberRipple(),
                                interactionSource = MutableInteractionSource()
                            )
                    ) {
                        Text(
                            text = item.text,
                            color = Color(if (multiplyTimes.value == item) 0xFFFFFFFF else 0x99FFFFFF),
                            modifier = Modifier.height(24.dp).requiredWidth(48.dp).wrapContentWidth().align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}