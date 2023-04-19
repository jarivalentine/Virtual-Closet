package be.howest.jarivalentine.virtualcloset.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import be.howest.jarivalentine.virtualcloset.data.outfits
import be.howest.jarivalentine.virtualcloset.data.tags

@Composable
fun OutfitScreen() {
    SearchField()
    Outfits()
}

@Composable
fun SearchField() {
    var text by remember { mutableStateOf("") }
    InputField(text) { text = it }
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
fun Outfit(outfit: String) {
    Column {
        //Image(painter = , contentDescription = )
        Row() {
            
        }
    }
}
