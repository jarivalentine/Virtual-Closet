package be.howest.jarivalentine.virtualcloset

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.howest.jarivalentine.virtualcloset.ui.*
import be.howest.jarivalentine.virtualcloset.ui.navigation.VirtualClosetNavHost
import be.howest.jarivalentine.virtualcloset.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

@Composable
fun VirtualClosetApp(
    viewModel: VirtualClosetViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = NavigationDestination.valueOf(
        backStackEntry?.destination?.route ?: NavigationDestination.Item.name
    )

    Scaffold(
        topBar = {
            TopBar(current = currentScreen, viewModel = viewModel, navController = navController)
        },
        bottomBar = {
            BottomNav(navController)
        },
        floatingActionButton = {
            if (currentScreen == NavigationDestination.Item) {
                FloatingActionButton(onClick = { navController.navigate(NavigationDestination.CreateItem.name) }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add")
                }
            }
        },
    ) { innerPadding ->
        VirtualClosetNavHost(modifier, innerPadding, navController, viewModel)
    }
}

@Composable
fun TopBar(
    current: NavigationDestination,
    viewModel: VirtualClosetViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (viewModel.selecting.value && current == NavigationDestination.Item) {
                SelectingRow(viewModel, onCreateOutfitClick = {
                    navController.navigate(NavigationDestination.CreateOutfit.name)
                })
            }
        }
        TopBarTitle(current.title)
    }
}

@Composable
fun SelectingRow(viewModel: VirtualClosetViewModel, onCreateOutfitClick: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    Icon(
        modifier = Modifier
            .padding(end = 20.dp)
            .size(35.dp)
            .clickable {
                coroutineScope.launch {
                    viewModel.deleteSelected()
                }
            },
        imageVector = Icons.Default.Delete,
        contentDescription = stringResource(R.string.delete_items),
    )
    Icon(
        modifier = Modifier
            .padding(end = 20.dp)
            .size(35.dp)
            .clickable {
                onCreateOutfitClick()
            },
        imageVector = Icons.Default.Favorite,
        contentDescription = stringResource(R.string.create_outfit),
    )
    Icon(
        modifier = Modifier
            .padding(end = 20.dp)
            .size(35.dp)
            .clickable {
                coroutineScope.launch {
                    viewModel.toggleAvailable()
                }
            },
        painter = painterResource(id = R.drawable.output_onlinepngtools),
        contentDescription = stringResource(
            R.string.toggle_available
        )
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
            .height(60.dp)
            .background(MaterialTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavButton(Icons.Filled.Home) {
            navController.navigate(NavigationDestination.Item.name)
        }
        BottomNavButton(Icons.Filled.Favorite) {
            navController.navigate(NavigationDestination.Outfit.name)
        }
        BottomNavButton(Icons.Filled.AccountBox) {
            navController.navigate(NavigationDestination.Profile.name)
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
