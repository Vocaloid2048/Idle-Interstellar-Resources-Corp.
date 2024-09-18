package com.voc.idle.irc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.voc.idle.irc.R
import com.voc.idle.irc.components.LineOrientation
import com.voc.idle.irc.components.NonLazyGrid
import com.voc.idle.irc.components.SpacerDashLine
import com.voc.idle.irc.utils.UtilTools
import screens.MakeBackground
import utils.FontShadow
import utils.FontSizeNormal12
import utils.FontSizeNormal14
import utils.IRCTheme
import kotlin.math.sin

@Composable
fun HomePageScreen() {
    var selectedItem = remember { mutableStateOf(0) }

    //Root Frame of the HomePage
    Box(modifier = Modifier.fillMaxSize()) {

        //The UIs of the HomePage
        Column(modifier = Modifier.fillMaxSize()) {
            //Currency Row, Gift Button, HAVE TO REPACK AS COMPONENT
            CurrencyUI()

            Spacer(modifier = Modifier.height(8.dp))

            //Main Content 主要內容
            Box(modifier = Modifier.fillMaxSize().weight(1f)) {
                when(selectedItem.value){
                    0 -> RedeemPage()
                    
                }
            }

            //Bottom Navigation Bar 導航欄, HAVE TO REPACK AS COMPONENT
            BottomNavigationBar(selectedItem)


        }

    }
}

@Preview()
@Composable
fun ItemView(currHave : Int = 0, ableToBuy : Int = 0){
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .border(1.dp, if(ableToBuy > 0 ) Color.White else Color.Transparent, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = {}
            )
    ){
        //Image of that item 物品圖片
        Image(
            painter = painterResource(id = R.drawable.item_iron_img),
            contentDescription = "Item Image",
            modifier = Modifier
                .fillMaxSize()
        )

        //Column with item info 物品資訊
        Column (modifier = Modifier.fillMaxSize().padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            //Item name 物品名稱
            Text(
                "鐵礦",
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                style = FontSizeNormal14() + FontShadow(),
                textAlign = TextAlign.Center,

            )
            // Item amount 物品數量
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "x${currHave}",
                    color = Color.White,
                    style = FontSizeNormal12() + FontShadow(),
                    textAlign = TextAlign.Center,
                )

                if(ableToBuy > 0){
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        "+${ableToBuy}",
                        color = Color.Green,
                        style = FontSizeNormal12() + FontShadow(),
                        textAlign = TextAlign.Start
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            //Item provided currency count 物品提供的貨幣數量
            Row(modifier = Modifier.padding(start = 4.dp, end = 4.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.coding_band_round),
                    contentDescription = "Currency Icon",
                    modifier = Modifier
                        .height(16.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    "+123.45K/s",
                    color = Color.White,
                    modifier = Modifier.wrapContentWidth(),
                    style = FontSizeNormal12() + FontShadow(),
                )
            }
        }
    }
}

@Preview()
@Composable
fun CurrencyUI(){
    Row(
        modifier = Modifier.height(88.dp).fillMaxWidth()
    ) {
        //Currency Row 貨幣列
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .weight(1f)
        ) {
            NonLazyGrid(3, 6) {
                //Currency UI 貨幣UI
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                ) {
                    //Currency Icon 貨幣圖標
                    Image(
                        painter = painterResource(id = R.drawable.coding_band_round),
                        contentDescription = "Currency Icon",
                        modifier = Modifier
                            .height(28.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        UtilTools().formatDecimal(
                            10000000,
                            decimalPlaces = 2,
                            isUnited = true
                        ), modifier = Modifier.align(Alignment.CenterVertically),
                        style = FontSizeNormal14(),
                        color = Color.White
                    ) //Currency Value
                    //Currency Value
                }
            }
        }
        //Spacer Dash Line 間隔線
        SpacerDashLine(modifier = Modifier.fillMaxHeight().width(0.5.dp), orientation = LineOrientation.VERTICAL)

        //Gift Icon 禮物圖標
        Image(
            painter = painterResource(id = R.drawable.ui_icon_gift),
            contentDescription = "Gift Icon",
            modifier = Modifier
                .height(64.dp)
                .aspectRatio(1f)
                .align(Alignment.CenterVertically)
                .padding(8.dp),
            colorFilter = ColorFilter.tint(Color.White),
        )
    }
}

@Composable
fun BottomNavigationBar(selectedItem : MutableState<Int>) {
    val navigationBarItemArray = arrayOf("兌換", "升級", "研究", "轉讓", "設定")
    NavigationBar {
        navigationBarItemArray.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = when (item) {
                            "兌換" -> R.drawable.ui_icon_grid
                            "升級" -> R.drawable.ui_icon_upgrade
                            "研究" -> R.drawable.ui_icon_research
                            "轉讓" -> R.drawable.ui_icon_transfer
                            "設定" -> R.drawable.ui_icon_setting
                            else -> R.drawable.ui_icon_missing
                        }),
                        contentDescription = item,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(item) },
                selected = selectedItem.value == index,
                onClick = { selectedItem.value = index }
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePageScreenPreview() {
    IRCTheme(darkTheme = true) {
        Box(modifier = Modifier.fillMaxSize()) {
            MakeBackground()
            HomePageScreen()
        }
    }
}