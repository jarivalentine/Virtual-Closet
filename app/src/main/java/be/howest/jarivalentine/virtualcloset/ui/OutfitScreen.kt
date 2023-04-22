package be.howest.jarivalentine.virtualcloset.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jarivalentine.virtualcloset.R
import be.howest.jarivalentine.virtualcloset.data.Outfit
import be.howest.jarivalentine.virtualcloset.data.labels
import be.howest.jarivalentine.virtualcloset.data.outfits
import be.howest.jarivalentine.virtualcloset.ui.theme.Shapes
import be.howest.jarivalentine.virtualcloset.ui.theme.VirtualClosetTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@Composable
fun OutfitScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SearchField()
        Outfits()
    }
}

@Composable
fun SearchField() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
/*        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Black,
            focusedIndicatorColor = Color.Gray,
            cursorColor = Color.White,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Gray
        ),*/
        label = { Text(text = stringResource(R.string.search)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "",
            )
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Outfits() {
    SlidingCarousel(
        itemsCount = outfits.size,
        itemContent = { index ->
            Outfit(outfits[index])
        }
    )
}

@Composable
fun Outfit(outfit: Outfit) {
    Card {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = outfit.imageResourceId),
                contentDescription = stringResource(
                    id = outfit.name
                ),
                modifier = Modifier
                    .width(LocalConfiguration.current.screenWidthDp.dp - 20.dp)
                    .height(screenHeight - 240.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .width(LocalConfiguration.current.screenWidthDp.dp - 20.dp)
                    .align(Alignment.BottomStart)
                    .background(Color.DarkGray.copy(alpha = 0.5f)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Text(text = stringResource(id = outfit.name), fontSize = 20.sp)
                    Labels()
                }
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun Labels() {
    Row() {
        Label(labels[2])
        Label("Available")
    }
}

@Composable
fun Label(name: String) {
    Box(
        modifier = Modifier
            .padding(
                start = 10.dp,
                top = 10.dp,
                bottom = 10.dp,
                end = if (name == labels.last()) 10.dp else 0.dp
            )
            .background(shape = Shapes.small, color = MaterialTheme.colors.background)
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = name
        )
    }
}



@OptIn(ExperimentalPagerApi::class)
@Composable
fun SlidingCarousel(
    modifier: Modifier = Modifier,
    pagerState: PagerState = remember { PagerState() },
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            itemContent(page)
        }
        Surface(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .align(Alignment.CenterHorizontally),
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.5f)
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
        }
    }
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = Color.White,
    unSelectedColor: Color = Color.Gray,
    dotSize: Dp
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

@Preview(showBackground = true)
@Composable
fun OutfitPreview() {
    VirtualClosetTheme {
        OutfitScreen()
    }
}
