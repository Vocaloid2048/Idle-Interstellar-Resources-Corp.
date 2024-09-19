package com.voc.idle.irc.screens

import NavigationBar
import NavigationItemData
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.voc.idle.irc.components.HeaderData
import com.voc.idle.irc.components.HoverButtonBar
import com.voc.idle.irc.components.ItemGridView
import com.voc.idle.irc.components.ItemResearchView
import com.voc.idle.irc.components.ItemUpgradeView
import com.voc.idle.irc.components.LineOrientation
import com.voc.idle.irc.components.MultiplyEnum
import com.voc.idle.irc.components.NonLazyGrid
import com.voc.idle.irc.components.SpacerDashLine
import com.voc.idle.irc.getScreenSizeInfo
import com.voc.idle.irc.utils.UtilTools
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.Res
import files.coding_band_round
import files.item_iron_img
import files.ui_icon_gift
import files.ui_icon_grid
import files.ui_icon_missing
import files.ui_icon_research
import files.ui_icon_setting
import files.ui_icon_transfer
import files.ui_icon_upgrade
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import utils.FontShadow
import utils.FontSizeNormal12
import utils.FontSizeNormal14
import utils.FontSizeNormal16
import utils.IRCTheme
import utils.PageSafeArea

@Composable
fun HomePageScreen(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier,
    headerData: HeaderData = HeaderData()
) {
    var selectedItem = remember { mutableStateOf(0) }

    //Root Frame of the HomePage
    Box(modifier = Modifier.fillMaxSize().navigationBarsPadding().statusBarsPadding()) {

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
                    0 -> androidx.compose.animation.AnimatedVisibility(visible = true){ RedeemPage() }
                    1 -> androidx.compose.animation.AnimatedVisibility(visible = true){ UpgradePage() }
                    2 -> androidx.compose.animation.AnimatedVisibility(visible = true){ ResearchPage() }
                }
            }

            //Bottom Navigation Bar 導航欄, HAVE TO REPACK AS COMPONENT
            BottomNavigationBar(selectedItem)


        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RedeemPage() {
    val hazeState = remember { HazeState() }
    Box(){
        val screenWidthDp = getScreenSizeInfo().wDP
        val minItemSize = 96.dp
        val cells = if(screenWidthDp / 3 < minItemSize){GridCells.Fixed(3) } else GridCells.Adaptive(minItemSize)
        LazyVerticalGrid(
            columns = cells,
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 64.dp),
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
    val choiceList = arrayListOf<ImageVector>(vectorResource(Res.drawable.ui_icon_missing),vectorResource(Res.drawable.ui_icon_missing),vectorResource(Res.drawable.ui_icon_missing),vectorResource(Res.drawable.ui_icon_missing))

    //Root of Upgrade Page 升級頁面
    Column(modifier = Modifier.fillMaxSize()) {
        HoverButtonBar(choiceList = choiceList){ item, choosedIndex ->
            Image(
                imageVector = item,
                contentDescription = "Upgrade Filter Icon",
                colorFilter = ColorFilter.tint(Color(if (choiceList[choosedIndex] == item) 0xFFFFFFFF else 0x99FFFFFF)),
                modifier = Modifier.size(30.dp).aspectRatio(1f).align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.99f }
                .haze(hazeState),

        ) {
            items(20) {it ->
                Column {
                    ItemUpgradeView()
                    Divider()
                }
            }
        }
    }
}

@Composable
fun ResearchPage(){
    val hazeState = remember { HazeState() }
    val choiceList = arrayListOf<ImageVector>(vectorResource(Res.drawable.ui_icon_missing))

    //Root of Research Page 研究頁面
    Column(modifier = Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.99f }
                .haze(hazeState)
        ) {
            items(20) {it ->
               Column {
                   ItemResearchView()
                   Divider()
               }
            }
        }
    }
}

@Composable
fun CurrencyUI(){
    val screenWidthDp = getScreenSizeInfo().wDP - 8.dp - 80.dp
    val minItemSize = 120.dp
    val cells = if(screenWidthDp / 3 < minItemSize){GridCells.Fixed(3) } else GridCells.Adaptive(minItemSize)

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        //Currency Row 貨幣列
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = cells,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().wrapContentHeight().align(Alignment.CenterVertically)
            ) {
                items(5) {
                    //Currency UI 貨幣UI
                    Row(
                        modifier = Modifier
                    ) {
                        //Currency Icon 貨幣圖標
                        Image(
                            painter = painterResource(resource = Res.drawable.coding_band_round),
                            contentDescription = "Currency Icon",
                            modifier = Modifier
                                .height(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            UtilTools().formatDecimal(
                                10000000,
                                decimalPlaces = 2,
                                isUnited = true
                            ),
                            modifier = Modifier.align(Alignment.CenterVertically).wrapContentWidth(),
                            style = FontSizeNormal14(),
                            color = Color.White,
                            maxLines = 1,
                        )
                    }
                }
            }
        }
        //Spacer Dash Line 間隔線
        //SpacerDashLine(modifier = Modifier.fillMaxHeight().width(0.5.dp), orientation = LineOrientation.VERTICAL)

        //Gift Icon 禮物圖標
        Image(
            painter = painterResource(resource = Res.drawable.ui_icon_gift),
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
        NavigationItemData("兌換", vectorResource(resource = Res.drawable.ui_icon_grid)),
        NavigationItemData("升級", vectorResource(resource = Res.drawable.ui_icon_upgrade)),
        NavigationItemData("研究", vectorResource(resource = Res.drawable.ui_icon_research)),
        NavigationItemData("轉讓", vectorResource(resource = Res.drawable.ui_icon_transfer)),
        NavigationItemData("設定", vectorResource(resource = Res.drawable.ui_icon_setting)),
    )

    NavigationBar(
        items = items,
        selectedIndex = selectedItem.value,
        onItemSelected = { selectedItem.value = it }
    )

}

@Composable
fun HomePageScreenPreview() {
    IRCTheme(darkTheme = true) {
        Box(modifier = Modifier.fillMaxSize()) {
            MakeBackground()
            HomePageScreen()
        }
    }
}