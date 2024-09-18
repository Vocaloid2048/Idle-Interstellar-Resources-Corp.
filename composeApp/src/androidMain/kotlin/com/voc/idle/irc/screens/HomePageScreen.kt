package com.voc.idle.irc.screens

import NavigationBar
import NavigationItemData
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
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.voc.idle.irc.R
import com.voc.idle.irc.components.HoverButtonBar
import com.voc.idle.irc.components.LineOrientation
import com.voc.idle.irc.components.MultiplyEnum
import com.voc.idle.irc.components.NonLazyGrid
import com.voc.idle.irc.components.SpacerDashLine
import com.voc.idle.irc.utils.UtilTools
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import org.jetbrains.compose.resources.imageResource
import screens.MakeBackground
import utils.FontShadow
import utils.FontSizeNormal12
import utils.FontSizeNormal14
import utils.FontSizeNormal16
import utils.IRCTheme
import utils.PageSafeArea

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
            Box(modifier = Modifier
                .fillMaxSize()
                .weight(1f)) {
                when(selectedItem.value){
                    0 -> { RedeemPage() }
                    1 -> { UpgradePage() }
                }
            }

            //Bottom Navigation Bar 導航欄, HAVE TO REPACK AS COMPONENT
            BottomNavigationBar(selectedItem)


        }

    }
}

@Composable
fun RedeemPage() {
    val hazeState = remember { HazeState() }
    Box(){
        LazyVerticalGrid(
            columns = GridCells.Adaptive(120.dp),
            contentPadding = PaddingValues(8.dp, bottom = 64.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.99f }
                .haze(hazeState)

        ) {
            items(20) {it ->
                ItemGridView(it * 10, it)
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter).padding(8.dp)){
            HoverButtonBar(hazeState = hazeState, choiceList = MultiplyEnum.entries){item, choosedIndex ->
                Text(
                    text = item.text,
                    color = Color(if (MultiplyEnum.entries[choosedIndex] == item) 0xFFFFFFFF else 0x99FFFFFF),
                    modifier = Modifier.height(24.dp).requiredWidth(48.dp).wrapContentWidth().align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun UpgradePage(){
    val hazeState = remember { HazeState() }
    val choiceList = arrayListOf<ImageVector>(ImageVector.vectorResource(R.drawable.ui_icon_missing))

    //Root of Upgrade Page 升級頁面
    Column(modifier = Modifier.fillMaxSize().padding(start = PageSafeArea, end = PageSafeArea)) {
        HoverButtonBar(choiceList = choiceList){ item, choosedIndex ->
            Image(
                imageVector = item,
                contentDescription = "Upgrade Filter Icon",
                colorFilter = ColorFilter.tint(Color(if (choiceList[choosedIndex] == item) 0xFFFFFFFF else 0x99FFFFFF)),
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.99f }
                .haze(hazeState)
        ) {
             items(20) {it ->
                ItemUpgradeView()
            }
        }
    }
}

@Composable
//LATER PLEASE TURN IT TO DATA CLASS instaed of using parameters
fun ItemGridView(currHave : Int = 0, ableToBuy : Int = 0){
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .border(
                1.dp,
                if (ableToBuy > 0) Color.White else Color.Transparent,
                RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
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
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
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

@Composable
@Preview(showBackground = false)
fun ItemUpgradeView(){
    Row(Modifier.fillMaxWidth().height(80.dp).width(320.dp).padding(start = 8.dp, end = 8.dp).clip(RoundedCornerShape(8.dp))) {
        //Image of that item 物品圖片
        Box(
            modifier = Modifier
                .height(64.dp)
                .aspectRatio(1f)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(8.dp))
        ) {
            //Image of that item 物品圖片
            Image(
                painter = painterResource(id = R.drawable.item_iron_img),
                contentDescription = "Item Image",
                modifier = Modifier
                    .height(64.dp)
                    .aspectRatio(1f)
            )

            //Image of the currency icon 貨幣圖標
            Image(
                painter = painterResource(id = R.drawable.coding_band_round),
                contentDescription = "Upgrade Icon",
                modifier = Modifier
                    .height(24.dp)
                    .aspectRatio(1f)
                    .align(Alignment.BottomEnd)
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        //Column with item info 物品資訊
        Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
            Column(Modifier.wrapContentHeight().fillMaxWidth().weight(1f).padding(top = 4.dp, bottom = 4.dp, start = 8.dp)) {
                Text(
                    text = "鐵礦",
                    color = Color.White,
                    modifier = Modifier.wrapContentWidth().wrapContentHeight().weight(1f),
                    style = FontSizeNormal16() + FontShadow(),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "物品收益 +25%",
                    color = Color.Green,
                    modifier = Modifier.wrapContentWidth().wrapContentHeight().padding(start = 8.dp).weight(1f),
                    style = FontSizeNormal14() + FontShadow(),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "當前等級: 10 (+250%)",
                    color = Color.LightGray,
                    modifier = Modifier.wrapContentWidth().wrapContentHeight().padding(start = 8.dp).weight(1f),
                    style = FontSizeNormal14() + FontShadow(),
                    textAlign = TextAlign.Start,
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            //Upgrade Requirement 升級需求
            Column(modifier = Modifier.wrapContentHeight().wrapContentWidth().padding(top = 4.dp, bottom = 4.dp, start = 8.dp)) {
                Row(modifier = Modifier.wrapContentWidth().wrapContentHeight().weight(1f)) {
                    Image(
                        painter = painterResource(id = R.drawable.coding_band_round),
                        contentDescription = "Currency Icon",
                        modifier = Modifier
                            .height(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "100.00M",
                        color = Color.White,
                        modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                        style = FontSizeNormal14() + FontShadow(),
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}

@Composable
fun CurrencyUI(){
    Row(
        modifier = Modifier
            .height(88.dp)
            .fillMaxWidth()
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
                    )
                }
            }
        }
        //Spacer Dash Line 間隔線
        SpacerDashLine(modifier = Modifier
            .fillMaxHeight()
            .width(0.5.dp), orientation = LineOrientation.VERTICAL)

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
    val items = listOf(
        NavigationItemData("兌換", ImageVector.vectorResource(id = R.drawable.ui_icon_grid)),
        NavigationItemData("升級", ImageVector.vectorResource(id = R.drawable.ui_icon_upgrade)),
        NavigationItemData("研究", ImageVector.vectorResource(id = R.drawable.ui_icon_research)),
        NavigationItemData("轉讓", ImageVector.vectorResource(id = R.drawable.ui_icon_transfer)),
        NavigationItemData("設定", ImageVector.vectorResource(id = R.drawable.ui_icon_setting)),
    )

    NavigationBar(
        items = items,
        selectedIndex = selectedItem.value,
        onItemSelected = { selectedItem.value = it }
    )

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