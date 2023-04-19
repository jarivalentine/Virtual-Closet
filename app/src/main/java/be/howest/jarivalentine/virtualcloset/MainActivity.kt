package be.howest.jarivalentine.virtualcloset

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jarivalentine.virtualcloset.ui.ItemScreen
import be.howest.jarivalentine.virtualcloset.ui.CreateScreen
import be.howest.jarivalentine.virtualcloset.ui.OutfitScreen
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
/*        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add")
            }
        }*/
    ) {
        MainContent()
    }
}

@Composable
fun BottomNav(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavButton(Icons.Filled.Home)
        BottomNavButton(Icons.Filled.Search)
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
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainContent() {
    Scaffold(
        topBar = {
            TopBar(title = R.string.create_title)
        },
    ) {
        OutfitScreen()
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier, @StringRes title: Int) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TopBarMenu()
        TopBarTitle(title)
    }
}

@Composable
fun TopBarMenu() {
    Icon(
        Icons.Rounded.Menu,
        stringResource(R.string.menu),
        modifier = Modifier
            .padding(start = 10.dp)
            .size(40.dp)
    )
}

@Composable
fun TopBarTitle(@StringRes title: Int) {
    Text(
        text = stringResource(title),
        fontSize = 24.sp,
        modifier = Modifier.padding(end = 20.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VirtualClosetTheme {
        VirtualClosetApp()
    }
}