package be.howest.jarivalentine.virtualcloset

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.howest.jarivalentine.virtualcloset.data.Item
import be.howest.jarivalentine.virtualcloset.ui.theme.VirtualClosetTheme
import be.howest.jarivalentine.virtualcloset.data.items

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
        LazyColumn() {
            items(items) {
                ClosetItem(item = it)
            }
        }
    }
}

@Composable
fun BottomNav(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.home_title))
    }
}

@Composable
fun ClosetItem(item: Item, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(10.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = item.imageResourceId),
                contentDescription = stringResource(id = item.name)
            )
            Text(text = stringResource(id = item.name))
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