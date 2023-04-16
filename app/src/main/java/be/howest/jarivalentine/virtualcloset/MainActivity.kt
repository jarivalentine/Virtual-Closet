package be.howest.jarivalentine.virtualcloset

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.howest.jarivalentine.virtualcloset.data.Item
import be.howest.jarivalentine.virtualcloset.data.items
import be.howest.jarivalentine.virtualcloset.ui.ItemScreen
import be.howest.jarivalentine.virtualcloset.ui.theme.VirtualClosetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VirtualClosetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    VirtualClosetApp()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VirtualClosetApp() {
    Scaffold(
        bottomBar = {
            BottomNav()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* ... */ }, backgroundColor = Color.Black) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add", tint = Color.White)
            }
        }
    ) {
        ItemScreen()
    }
}

@Composable
fun BottomNav(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavButton(Icons.Filled.Home)
        BottomNavButton(Icons.Filled.AccountBox)
        BottomNavButton(Icons.Filled.Favorite)
    }
}

@Composable
fun BottomNavButton(icon: ImageVector) {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(35.dp),
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VirtualClosetTheme {
        VirtualClosetApp()
    }
}