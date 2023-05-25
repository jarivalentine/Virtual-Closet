package be.howest.jarivalentine.virtualcloset.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.sharp.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(
    viewModel: VirtualClosetViewModel
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        ProfileHeader()
        Spacer(modifier = Modifier.height(16.dp))
        ProfileInfo()
        Spacer(modifier = Modifier.height(16.dp))
        ProfileActions()
    }
}

@Composable
fun ProfileHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Sharp.AccountBox,
            contentDescription = "Profile Image",
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "John Doe",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "Software Engineer",
                style = MaterialTheme.typography.subtitle1,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ProfileInfo() {
    Column {
        ProfileInfoItem("Email", "john.doe@example.com")
        Spacer(modifier = Modifier.height(8.dp))
        ProfileInfoItem("Phone", "+1 (123) 456-7890")
        Spacer(modifier = Modifier.height(8.dp))
        ProfileInfoItem("Location", "New York, NY")
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.body1,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun ProfileActions() {
    Column {
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Edit Profile")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Logout")
        }
    }
}
