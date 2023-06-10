import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import be.howest.jarivalentine.virtualcloset.data.Brand
import be.howest.jarivalentine.virtualcloset.data.BrandRepository
import be.howest.jarivalentine.virtualcloset.data.Item
import be.howest.jarivalentine.virtualcloset.data.ItemRepository
import be.howest.jarivalentine.virtualcloset.data.Outfit
import be.howest.jarivalentine.virtualcloset.data.OutfitRepository
import be.howest.jarivalentine.virtualcloset.ui.VirtualClosetUiState
import be.howest.jarivalentine.virtualcloset.ui.VirtualClosetViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class VirtualClosetViewModelTest {

    private lateinit var viewModel: VirtualClosetViewModel
    private lateinit var mockItemRepository: ItemRepository
    private lateinit var mockOutfitRepository: OutfitRepository
    private lateinit var mockBrandRepository: BrandRepository

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        mockItemRepository = mockk()
        mockOutfitRepository = mockk()
        mockBrandRepository = mockk()

        coEvery { mockItemRepository.getAllItemsStream(null) } returns flowOf(
            listOf(
                Item(1, "Blue jacket", "Tops", "H&M", "https://i.imgur.com/pVOsyRO.png", true),
                Item(2, "Black jeans", "Bottoms", "H&M", "https://i.imgur.com/pVOsyRO.png", true)
            )
        )

        coEvery { mockOutfitRepository.getAllOutfitsStream(null) } returns flowOf(
            listOf(
                Outfit(1, "Outfit 1", "Summer"),
                Outfit(2, "Outfit 2", "Winter")
            )
        )

        coEvery { mockBrandRepository.getBrands() } returns listOf(
            Brand("H&M", null),
        )

        viewModel = VirtualClosetViewModel(mockItemRepository, mockOutfitRepository, mockBrandRepository)
    }

    @Test
    fun toggleSelectTests() = runTest {
        assertEquals(false, viewModel.selecting.value)

        viewModel.toggleSelect(1)

        assertEquals(listOf(1), viewModel.selectedItems.value)
        assertEquals(true, viewModel.selecting.value)

        viewModel.toggleSelect(2)

        assertEquals(listOf(1, 2), viewModel.selectedItems.value)
        assertEquals(true, viewModel.selecting.value)

        viewModel.toggleSelect(1)

        assertEquals(listOf(2), viewModel.selectedItems.value)
        assertEquals(true, viewModel.selecting.value)

        viewModel.toggleSelect(2)

        assertEquals(false, viewModel.selecting.value)
    }
}