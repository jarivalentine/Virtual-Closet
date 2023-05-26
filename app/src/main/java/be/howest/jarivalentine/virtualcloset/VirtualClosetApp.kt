package be.howest.jarivalentine.virtualcloset

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.howest.jarivalentine.virtualcloset.ui.*
import be.howest.jarivalentine.virtualcloset.ui.theme.VirtualClosetTheme
import kotlinx.coroutines.launch

enum class VirtualClosetScreen(@StringRes val title: Int) {
    Item(title = R.string.item_title),
    CreateItem(title = R.string.create_item_title),
    CreateOutfit(title = R.string.create_outfit_title),
    Outfit(title = R.string.outfit_title),
    Profile(title = R.string.profile_title)
}

@Composable
fun VirtualClosetApp(
    viewModel: VirtualClosetViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = VirtualClosetScreen.valueOf(
        backStackEntry?.destination?.route ?: VirtualClosetScreen.Item.name
    )

    Scaffold(
        topBar = {
            TopBar(title = currentScreen.title, viewModel = viewModel)
        },
        bottomBar = {
            BottomNav(navController)
        },
        floatingActionButton = {
            if (currentScreen == VirtualClosetScreen.Item) {
                FloatingActionButton(onClick = { navController.navigate(VirtualClosetScreen.CreateItem.name) }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add")
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = VirtualClosetScreen.Item.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = VirtualClosetScreen.Item.name) {
                ItemScreen(viewModel)
            }
            composable(route = VirtualClosetScreen.Outfit.name) {
                OutfitScreen(viewModel)
            }
            composable(route = VirtualClosetScreen.CreateItem.name) {
                CreateScreen(
                    viewModel,
                    onCancelClick = { navController.popBackStack() },
                    onCreateClick = { navController.navigateUp() }
                )
            }
            composable(route = VirtualClosetScreen.Profile.name) {
                ProfileScreen(viewModel)
            }
        }
    }
}

@Composable
fun TopBar(
    @StringRes title: Int,
    viewModel: VirtualClosetViewModel,
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
            if (viewModel.selecting.value) {
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
                            coroutineScope.launch {
                                viewModel.createOutfit()
                            }
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
        }
        TopBarTitle(title)
    }
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
            navController.navigate(VirtualClosetScreen.Item.name)
        }
        BottomNavButton(Icons.Filled.Favorite) {
            navController.navigate(VirtualClosetScreen.Outfit.name)
        }
        BottomNavButton(Icons.Filled.AccountBox) {
            navController.navigate(VirtualClosetScreen.Profile.name)
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
