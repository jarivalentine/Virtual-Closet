package be.howest.jarivalentine.virtualcloset

import android.annotation.SuppressLint
import android.content.ClipData.Item
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.howest.jarivalentine.virtualcloset.ui.ItemScreen
import be.howest.jarivalentine.virtualcloset.ui.CreateScreen
import be.howest.jarivalentine.virtualcloset.ui.OutfitScreen
import be.howest.jarivalentine.virtualcloset.ui.VirtualClosetViewModel
import be.howest.jarivalentine.virtualcloset.ui.theme.VirtualClosetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VirtualClosetTheme {
                VirtualClosetApp()
            }
        }
    }
}
