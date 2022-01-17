///*
// * Copyright 2020 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package screens
//
//import androidx.compose.foundation.ScrollState
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.ArrowBack
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.layout.Layout
//import androidx.compose.ui.unit.Constraints
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.lerp
//import androidx.compose.ui.unit.sp
////import androidx.compose.ui.util.lerp
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.*
//import factories.LoginItems
//import navigation.NavController
//import java.math.BigDecimal
//import java.text.NumberFormat
//import kotlin.math.max
//import kotlin.math.min
//import kotlin.random.Random
//
//private val BottomBarHeight = 56.dp
//private val TitleHeight = 128.dp
//private val GradientScroll = 180.dp
//private val ImageOverlap = 115.dp
//private val MinTitleOffset = 56.dp
//private val MinImageOffset = 12.dp
//private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
//private val ExpandedImageSize = 300.dp
//private val CollapsedImageSize = 150.dp
//private val HzPadding = Modifier.padding(horizontal = 24.dp)
//
//@Composable
//fun AuctionScreen(
//    auctionId: Int,
//    upPress: () -> Unit
//) {
//    // val thisAuction = LiveA
//    val auction = remember() { auctionDummyData(auctionId,"Georg Jensen Stol",5000,39) }
//    val (auctionTitle, setAuctionTitle) = remember { mutableStateOf(Random.Default) }
//
//
//    Box(Modifier.fillMaxSize()) {
//        val scroll = rememberScrollState(0)
//        Header()
//        Body(auction, scroll)
//        Title(auction, scroll.value)
//        //Image("image-url", scroll.value)
//        //Up(upPress)
//    }
//}
//
//@Composable
//private fun Header() {
//    Spacer(
//        modifier = Modifier
//            .height(280.dp)
//            .fillMaxWidth()
//            .background(Brush.horizontalGradient(listOf(Color(0xff7057f5), Color(0xff57eff5))))
//    )
//}
//
//@Composable
//private fun Up(upPress: () -> Unit) {
//    IconButton(
//        onClick = upPress,
//        modifier = Modifier
//            //.statusBarsPadding()
//            .padding(horizontal = 16.dp, vertical = 10.dp)
//            .size(36.dp)
//            .background(
//                color = Color(0xff121212).copy(alpha = 0.32f),
//                shape = CircleShape
//            )
//    ) {
//        Icon(
//            imageVector = Icons.Outlined.ArrowBack,
//            tint = Color(0xffffffff),
//            contentDescription = "Back"
//        )
//    }
//}
//
//@Composable
//private fun Body(
//    auction: auctionDummyData,
//    scroll: ScrollState
//) {
//    Column {
//        Spacer(
//            modifier = Modifier
//                .fillMaxWidth()
//                //.statusBarsPadding()
//                .height(MinTitleOffset)
//        )
//        Column(
//            modifier = Modifier.verticalScroll(scroll)
//        ) {
//            Spacer(Modifier.height(GradientScroll))
//            Surface(Modifier.fillMaxWidth()) {
//                Column {
//                    Spacer(Modifier.height(ImageOverlap))
//                    Spacer(Modifier.height(TitleHeight))
//
//                    Spacer(Modifier.height(16.dp))
//                    Text(
//                        text = "Details",
//                        style = MaterialTheme.typography.overline,
//                        color = Color(0xbdffffff),
//                        modifier = HzPadding
//                    )
//                    Spacer(Modifier.height(16.dp))
//                    var seeMore by remember { mutableStateOf(true) }
//                    Text(
//                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut tempus, sem vitae convallis imperdiet, lectus nunc pharetra diam, ac rhoncus quam eros eu risus. Nulla pulvinar condimentum erat, pulvinar tempus turpis blandit ut. Etiam sed ipsum sed lacus eleifend hendrerit eu quis quam. Etiam ligula eros, finibus vestibulum tortor ac, ultrices accumsan dolor. Vivamus vel nisl a libero lobortis posuere. Aenean facilisis nibh vel ultrices bibendum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Suspendisse ac est vitae lacus commodo efficitur at ut massa. Etiam vestibulum sit amet sapien sed varius. Aliquam non ipsum imperdiet, pulvinar enim nec, mollis risus. Fusce id tincidunt nisl.",
//                        style = MaterialTheme.typography.body1,
//                        color = Color(0xbdffffff),
//                        maxLines = if (seeMore) 5 else Int.MAX_VALUE,
//                        overflow = TextOverflow.Ellipsis,
//                        modifier = HzPadding
//                    )
//                    val textButton = if (seeMore) {
//                        "See more"
//                    } else {
//                        "See less"
//                    }
//                    Text(
//                        text = textButton,
//                        style = MaterialTheme.typography.button,
//                        textAlign = TextAlign.Center,
//                        color = Color(0xff005687),
//                        modifier = Modifier
//                            .heightIn(20.dp)
//                            .fillMaxWidth()
//                            .padding(top = 15.dp)
//                            .clickable {
//                                seeMore = !seeMore
//                            }
//                    )
//                    Spacer(
//                        modifier = Modifier
//                            .padding(bottom = BottomBarHeight)
//                            //.navigationBarsPadding(start = false, end = false)
//                            .height(8.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun Title(auction: auctionDummyData, scroll: Int) {
//    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
//    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }
//    val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
//    Column(
//        verticalArrangement = Arrangement.Bottom,
//        modifier = Modifier
//            .heightIn(min = TitleHeight)
//            //.statusBarsPadding()
//            .graphicsLayer { translationY = offset }
//            .background(Color(24, 25, 29))
//    ) {
//        Spacer(Modifier.height(16.dp))
//        Text(
//            text = auction.AuctionTitle,
//            style = MaterialTheme.typography.h4,
//            color = Color(24, 25, 29),
//            modifier = HzPadding
//        )
//        Text(
//            text = "Bidders: " + auction.TimeRemaining.toString(),
//            style = MaterialTheme.typography.subtitle2,
//            fontSize = 20.sp,
//            color = Color(24, 25, 29),
//            modifier = HzPadding
//        )
//        Spacer(Modifier.height(4.dp))
//        Text(
//             text = formatPrice(auction.AuctionPrice),
//            style = MaterialTheme.typography.h6,
//            color = Color(48, 163, 230),
//            modifier = HzPadding
//        )
//
//        Spacer(Modifier.height(8.dp))
//        Divider(
//            modifier=  Modifier,
//        color = Color(0x1f000000),
//        thickness =1.dp,
//        startIndent =  0.dp
//        )
//    }
//}
//
//fun formatPrice(price: Int): String {
//    return NumberFormat.getCurrencyInstance().format(
//        BigDecimal(price).movePointLeft(2)
//    )
//}
//
//@Composable
//fun Image(
//    imageUrl: String,
//    scroll: Int
//) {
//    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
//    val collapseFraction = (scroll / collapseRange).coerceIn(0f, 1f)
//    /*
//    CollapsingImageLayout(
//        collapseFraction = collapseFraction,
//        //modifier = HzPadding.then(Modifier.statusBarsPadding())
//    ) {*/
//        AuctionImage(
//            imageUrl = imageUrl,
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize()
//        )
//    }
////}
//
//@Composable
//fun AuctionImage(
//    imageUrl: String,
//    contentDescription: String?,
//    modifier: Modifier = Modifier,
//    elevation: Dp = 0.dp
//) {
//    Surface(
//        color = Color.LightGray,
//        elevation = elevation,
//        shape = CircleShape,
//        modifier = modifier
//    ) { /*
//        Image(
//            painter = rememberImagePainter(
//                data = imageUrl,
//                builder = {
//                    crossfade(true)
//                    placeholder(drawableResId = R.drawable.placeholder)
//                }
//            ),
//            contentDescription = contentDescription,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop,
//        ) */
//    }
//}
///*
//@Composable
//private fun CollapsingImageLayout(
//    collapseFraction: Float,
//    modifier: Modifier = Modifier,
//    content: @Composable () -> Unit
//) {
//    Layout(
//        modifier = modifier,
//        content = content
//    ) { measurables, constraints ->
//        check(measurables.size == 1)
//
//        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
//        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
//        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
//        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))
//
//        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
//        val imageX = lerp(
//            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
//            constraints.maxWidth - imageWidth, // right aligned when collapsed
//            collapseFraction
//        )
//        layout(
//            width = constraints.maxWidth,
//            height = imageY + imageWidth
//        ) {
//            imagePlaceable.placeRelative(imageX, imageY)
//        }
//    }
//}*/
//@Composable
//fun makingABid() {
//    Column(
//        modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f).padding(40.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row(modifier = Modifier.padding(20.dp)) {
//            Text("Create new Auction", fontSize = 30.sp)
//        }
//        Row(modifier = Modifier.padding(5.dp)) {
//            TextField(
//                value = LoginItems.userName,
//                readOnly = true,
//                onValueChange = {},
//                label = { Text("Username") },
//                placeholder = { Text("Username") }
//            )
//        }
//    }
//}
//
//
//
