package be.howest.jarivalentine.virtualcloset

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.howest.jarivalentine.virtualcloset.ui.ItemScreen
import be.howest.jarivalentine.virtualcloset.ui.OutfitScreen
import be.howest.jarivalentine.virtualcloset.ui.VirtualClosetViewModel
import be.howest.jarivalentine.virtualcloset.ui.theme.VirtualClosetTheme

enum class VirtualClosetScreen(@StringRes val title: Int) {
    Item(title = R.string.item_title),
    CreateItem(title = R.string.create_item_title),
    CreateOutfit(title = R.string.create_outfit_title),
    Outfit(title = R.string.outfit_title)
}

@Composable
fun VirtualClosetApp(
    modifier: Modifier = Modifier,
    viewModel: VirtualClosetViewModel = VirtualClosetViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = VirtualClosetScreen.valueOf(
        backStackEntry?.destination?.route ?: VirtualClosetScreen.Item.name
    )

    Scaffold(
        topBar = {
            TopBar(title = R.string.create_title)
        },
        bottomBar = {
            BottomNav(navController)
        },
/*        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add")
            }
        }*/
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = VirtualClosetScreen.Item.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = VirtualClosetScreen.Item.name) {
                ItemScreen()
            }
            composable(route = VirtualClosetScreen.Outfit.name) {
                OutfitScreen()
            }
        }
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier, @StringRes title: Int) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colors.primary),
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

@Composable
fun BottomNav(navController: NavHostController, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MaterialTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavButton(Icons.Filled.Home) {
            navController.navigate(VirtualClosetScreen.Item.name)
        }
        BottomNavButton(Icons.Filled.Search) {

        }
        BottomNavButton(Icons.Filled.AccountBox) {

        }
        BottomNavButton(Icons.Filled.Favorite) {
            navController.navigate(VirtualClosetScreen.Outfit.name)
        }
    }
}

@Composable
fun BottomNavButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(35.dp),
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    VirtualClosetTheme {
        VirtualClosetApp()
    }
}