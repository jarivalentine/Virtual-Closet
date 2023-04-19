package be.howest.jarivalentine.virtualcloset.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jarivalentine.virtualcloset.R
import be.howest.jarivalentine.virtualcloset.data.Outfit
import be.howest.jarivalentine.virtualcloset.data.labels
import be.howest.jarivalentine.virtualcloset.data.outfits
import be.howest.jarivalentine.virtualcloset.data.tags
import be.howest.jarivalentine.virtualcloset.ui.theme.Shapes

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
            .fillMaxWidth(),
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

@Composable
fun Outfits() {
    LazyRow() {
        items(outfits) {
            Outfit(it)
        }
    }
}

@Composable
fun Outfit(outfit: Outfit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = outfit.imageResourceId),
            contentDescription = stringResource(
                id = outfit.name
            ),
            modifier = Modifier
                .width(LocalConfiguration.current.screenWidthDp.dp - 20.dp)
                .height(LocalConfiguration.current.screenHeightDp.dp - 300.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Column() {
                Text(text = stringResource(id = outfit.name), fontSize = 20.sp)
                LazyRow() {
                    items(labels) {
                        Label(name = it)
                    }
                }
            }
        }
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
