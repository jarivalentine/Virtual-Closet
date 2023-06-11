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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.howest.jarivalentine.virtualcloset.ui.*
import kotlinx.coroutines.launch

enum class VirtualClosetScreen(@StringRes val title: Int) {
    Item(title = R.string.item_title),
    CreateItem(title = R.string.create_item_title),
    CreateOutfit(title = R.string.create_outfit_title),
    Outfit(title = R.string.outfit_title),
    Profile(title = R.string.profile_title),
    Camera(title = R.string.camera_title)
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
            TopBar(current = currentScreen, viewModel = viewModel, navController = navController)
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
                val coroutineScope = rememberCoroutineScope()
                val itemUiState = viewModel.itemUiState
                CreateScreen(
                    onCancelClick = { navController.popBackStack() },
                    onCreateClick = {
                        coroutineScope.launch {
                            viewModel.saveItem()
                            navController.navigateUp()
                        }
                    },
                    onNameValueChange = {
                        viewModel.updateItemUiState(itemUiState.copy(name = it))
                    },
                    onTypeValueChange = {
                        viewModel.updateItemUiState(itemUiState.copy(type = it))
                    },
                    onImageChange = {
                        viewModel.updateItemUiState(itemUiState.copy(imageUri = it))
                    },
                    onBrandValueChange = { name, url ->
                        viewModel.updateItemUiState(
                            itemUiState.copy(
                                brand = name,
                                brandImage = url ?: ""
                            )
                        )
                    },
                    name = itemUiState.name,
                    type = itemUiState.type,
                    brand = itemUiState.brand,
                    isActive = itemUiState.actionEnabled,
                    viewModel = viewModel
                )
            }
            composable(route = VirtualClosetScreen.CreateOutfit.name) {
                val coroutineScope = rememberCoroutineScope()
                val outfitUiState = viewModel.outfitUiState
                val context = LocalContext.current
                CreateScreen(
                    onCancelClick = { navController.popBackStack() },
                    onCreateClick = {
                        coroutineScope.launch {
                            viewModel.saveOutfit(context)
                            navController.navigate(VirtualClosetScreen.Outfit.name)
                        }
                    },
                    onNameValueChange = {
                        viewModel.updateOutfitUiState(outfitUiState.copy(name = it))
                    },
                    onTypeValueChange = {
                        viewModel.updateOutfitUiState(outfitUiState.copy(season = it))
                    },
                    onImageChange = {
                        viewModel.updateOutfitUiState(outfitUiState.copy(imageUri = it))
                    },
                    name = outfitUiState.name,
                    type = outfitUiState.season,
                    isActive = outfitUiState.actionEnabled,
                    viewModel = null,
                    onBrandValueChange = null,
                    brand = null
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
    current: VirtualClosetScreen,
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
            if (viewModel.selecting.value && current == VirtualClosetScreen.Item) {
                SelectingRow(viewModel, onCreateOutfitClick = {
                    navController.navigate(VirtualClosetScreen.CreateOutfit.name)
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
