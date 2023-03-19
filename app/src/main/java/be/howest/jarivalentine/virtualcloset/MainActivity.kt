package be.howest.jarivalentine.virtualcloset

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Home
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
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier.background(MaterialTheme.colors.primary),
            columns = GridCells.Fixed(2)
        ) {
            items(items) {
                ClosetItem(item = it)
            }
        }
    }
}

@Composable
fun BottomNav(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
            .height(60.dp),
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
            modifier = Modifier.size(40.dp),
            tint = Color.White
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
            modifier = Modifier.background(MaterialTheme.colors.primaryVariant)
        ) {
            Image(
                modifier = Modifier.height(250.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = item.imageResourceId),
                contentDescription = stringResource(id = item.name)
            )
            Text(
                text = stringResource(id = item.name),
                color = Color.White,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VirtualClosetTheme {
        VirtualClosetApp()
    }
}