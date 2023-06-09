package be.howest.jarivalentine.virtualcloset.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.howest.jarivalentine.virtualcloset.R
import be.howest.jarivalentine.virtualcloset.data.Item
import be.howest.jarivalentine.virtualcloset.ui.theme.Shapes
import coil.compose.AsyncImage
import coil.request.ImageRequest

val tags = listOf("Tops", "Bottoms", "Shoes", "Accessories")

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
            items = uiState.itemList,
            onSelect = viewModel::toggleSelect,
            selected = viewModel.selectedItems.value,
            selecting = viewModel.selecting.value
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
fun ClosetItems(
    items: List<Item>,
    onSelect: (Int) -> Unit,
    selected: List<Int>,
    selecting: Boolean
) {
    if (items.isNotEmpty()) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2)
        ) {
            items(items) {
                ClosetItem(
                    item = it,
                    onSelect = onSelect,
                    isSelected = selected.contains(it.id),
                    selecting = selecting
                )
            }
        }
    } else {
        Text(text = "You have no items in your closet", modifier = Modifier.padding(10.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClosetItem(
    item: Item,
    onSelect: (Int) -> Unit,
    isSelected: Boolean,
    selecting: Boolean,
    modifier: Modifier = Modifier
) {
    Box {
        Card(
            modifier = modifier
                .padding(10.dp)
                .combinedClickable(
                    onClick = {
                        onSelect(item.id)
                    }
                ),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.background(MaterialTheme.colors.primaryVariant)
            ) {
                ClosetItemImage(item)
                ClosetItemText(item.name)
            }
        }
        AsyncImage(
            modifier = Modifier
                .width(75.dp)
                .padding(20.dp),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(item.brandImage)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.logo_description),
            contentScale = ContentScale.Fit,
        )
        if (selecting) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .padding(15.dp)
                    .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
        if (!item.available) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(10.dp)
                    .background(Color.Gray.copy(alpha = 0.5f), shape = MaterialTheme.shapes.medium),
            )
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
