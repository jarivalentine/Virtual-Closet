package be.howest.jarivalentine.virtualcloset.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OutfitScreen(modifier: Modifier = Modifier) {
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