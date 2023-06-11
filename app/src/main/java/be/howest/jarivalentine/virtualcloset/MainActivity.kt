package be.howest.jarivalentine.virtualcloset

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import be.howest.jarivalentine.virtualcloset.ui.VirtualClosetViewModel
import be.howest.jarivalentine.virtualcloset.ui.theme.VirtualClosetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VirtualClosetTheme {
                val viewModel: VirtualClosetViewModel = viewModel(factory = VirtualClosetViewModel.Factory)
                VirtualClosetApp(viewModel)
            }
        }
    }
}
