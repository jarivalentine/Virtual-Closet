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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import be.howest.jarivalentine.virtualcloset.VirtualClosetScreen
import be.howest.jarivalentine.virtualcloset.data.Item
import be.howest.jarivalentine.virtualcloset.ui.theme.Shapes
import be.howest.jarivalentine.virtualcloset.ui.theme.VirtualClosetTheme

val tags = listOf("All", "Tops", "Bottoms", "Shoes", "Accessories")

@Composable
fun ItemScreen(
    viewModel: VirtualClosetViewModel
) {
    val uiState by viewModel.virtualClosetUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        FilterTags()
        ClosetItems(
            items = uiState.itemList
        )
    }
}

@Composable
fun FilterTags() {
    LazyRow {
        items(tags) {
            FilterTag(it)
        }
    }
    Divider(thickness = 1.dp, color = MaterialTheme.colors.primary)
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
            .background(shape = Shapes.small, color = MaterialTheme.colors.secondary)
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = name,
            color = MaterialTheme.colors.onSecondary,
        )
    }
}

@Composable
fun ClosetItems(items: List<Item>) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(2)
    ) {
        items(items) {
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
            modifier = Modifier.background(MaterialTheme.colors.primaryVariant)
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
        painter = painterResource(id = R.drawable.hoodie1),
        contentDescription = item.name
    )
}

@Composable
fun ClosetItemText(name: String) {
    Text(
        text = name,
        modifier = Modifier.padding(5.dp),
        color = MaterialTheme.colors.onPrimary,
    )
}
