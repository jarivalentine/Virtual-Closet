package be.howest.jarivalentine.virtualcloset.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import be.howest.jarivalentine.virtualcloset.ui.screens.CreateScreen
import be.howest.jarivalentine.virtualcloset.ui.screens.ItemScreen
import be.howest.jarivalentine.virtualcloset.ui.screens.OutfitScreen
import be.howest.jarivalentine.virtualcloset.ui.screens.ProfileScreen
import be.howest.jarivalentine.virtualcloset.ui.VirtualClosetViewModel
import kotlinx.coroutines.launch

@Composable
fun VirtualClosetNavHost(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    navController: NavHostController,
    viewModel: VirtualClosetViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Item.name,
        modifier = modifier.padding(innerPadding)
    ) {
        composable(route = NavigationDestination.Item.name) {
            ItemScreen(viewModel)
        }
        composable(route = NavigationDestination.Outfit.name) {
            OutfitScreen(viewModel)
        }
        composable(route = NavigationDestination.CreateItem.name) {
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
        composable(route = NavigationDestination.CreateOutfit.name) {
            val coroutineScope = rememberCoroutineScope()
            val outfitUiState = viewModel.outfitUiState
            val context = LocalContext.current
            CreateScreen(
                onCancelClick = { navController.popBackStack() },
                onCreateClick = {
                    coroutineScope.launch {
                        viewModel.saveOutfit(context)
                        navController.navigate(NavigationDestination.Outfit.name)
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
        composable(route = NavigationDestination.Profile.name) {
            ProfileScreen(viewModel)
        }
    }
}