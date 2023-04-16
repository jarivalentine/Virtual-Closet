package be.howest.jarivalentine.virtualcloset.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jarivalentine.virtualcloset.R
import be.howest.jarivalentine.virtualcloset.VirtualClosetApp
import be.howest.jarivalentine.virtualcloset.data.Item
import be.howest.jarivalentine.virtualcloset.data.items
import be.howest.jarivalentine.virtualcloset.data.tags
import be.howest.jarivalentine.virtualcloset.ui.theme.Shapes
import be.howest.jarivalentine.virtualcloset.ui.theme.VirtualClosetTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ItemScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopBar()
        }
    ) {
        Column {
            FilterTags()
            ClosetItems()
        }
    }
}

@Composable
fun ClosetItems() {
    LazyVerticalGrid(
        modifier = Modifier
            .background(Color.White)
            .padding(bottom = 70.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(items) {
            ClosetItem(item = it)
        }
    }
}

@Composable
fun FilterTags() {
    LazyRow(
        modifier = Modifier.background(Color.White)
    ) {
        items(tags) {
            FilterTag(it)
        }
    }
    Divider(color = Color.Black, thickness = 2.dp)
}

@Composable
fun FilterTag(name: String) {
    Box(
        modifier = Modifier
            .padding(
                start = 10.dp,
                top = 10.dp,
                bottom = 10.dp,
                end = if (name == tags.last()) 10.dp else 0.dp
            )
            .background(shape = Shapes.small, color = Color.Black)
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = name
        )
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Rounded.Menu,
            stringResource(R.string.menu),
            modifier = Modifier
                .padding(start = 10.dp)
                .size(40.dp)
        )
        Text(
            text = stringResource(R.string.item_screen_title),
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.padding(end = 20.dp)
        )
    }
}

@Composable
fun ClosetItem(item: Item, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.background(Color.Black)
        ) {
            ClosetItemImage(item)
            ClosetItemText(item.name)
        }
    }
}

@Composable
fun ClosetItemImage(item: Item) {
    Image(
        modifier = Modifier.height(200.dp),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = item.imageResourceId),
        contentDescription = stringResource(id = item.name)
    )
}

@Composable
fun ClosetItemText(@StringRes name: Int) {
    Text(
        text = stringResource(id = name),
        color = Color.White,
        modifier = Modifier.padding(5.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VirtualClosetTheme {
        VirtualClosetApp()
    }
}
