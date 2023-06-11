package be.howest.jarivalentine.virtualcloset.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import be.howest.jarivalentine.virtualcloset.ui.VirtualClosetViewModel

@Composable
fun ProfileScreen() {
    Column {
        Box {
            WaveFigure()
            ProfileHeader()
        }
        Spacer(modifier = Modifier.height(16.dp))
        ProfileInfo()
        Spacer(modifier = Modifier.height(16.dp))
        ProfileActions()
    }
}

@Composable
fun WaveFigure() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val waveAmplitude = canvasHeight * 0.5f
        val waveOffset = waveAmplitude

        val path = Path().apply {
            moveTo(0f, waveOffset)
            cubicTo(
                canvasWidth * 0.2f, waveOffset - waveAmplitude,
                canvasWidth * 0.8f, waveOffset - waveAmplitude,
                canvasWidth, waveOffset
            )
            lineTo(canvasWidth, 0f)
            lineTo(0f, 0f)
            close()
        }

        val waveColor = Color(0xFF245953)

        drawPath(
            path = path,
            color = waveColor,
        )
    }
}

@Composable
fun ProfileHeader() {
    Row(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
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
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
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
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
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
