package be.howest.jarivalentine.virtualcloset.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.howest.jarivalentine.virtualcloset.data.Item

@Composable
fun ItemScreen(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = Modifier.background(Color.White),
        columns = GridCells.Fixed(2)
    ) {
        items(be.howest.jarivalentine.virtualcloset.data.items) {
            ClosetItem(item = it)
        }
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
        modifier = Modifier.height(250.dp),
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