package be.howest.jarivalentine.virtualcloset.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import be.howest.jarivalentine.virtualcloset.R
import be.howest.jarivalentine.virtualcloset.data.tags
import be.howest.jarivalentine.virtualcloset.ui.theme.VirtualClosetTheme

@Composable
fun CreateScreen(
    onCancelClick: () -> Unit,
    onCreateClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ItemNameTextField()
        ItemTypeDropdown()
        ImageUploadButton()
        ControlButtons(onCancelClick, onCreateClick)
    }
}

@Composable
fun ItemNameTextField() {
    var text by remember { mutableStateOf("") }
    InputField(text) { text = it }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemTypeDropdown() {
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedItem by remember {
        mutableStateOf(tags[0])
    }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        DropdownTextField(selectedItem, expanded)
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            tags.forEach { selected ->
                DropdownMenuItem(onClick = {
                    selectedItem = selected
                    expanded = false
                }) {
                    Text(text = selected)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownTextField(selectedItem: String, isExpanded: Boolean) {
    OutlinedTextField(
        value = selectedItem,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth(),
        readOnly = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = MaterialTheme.colors.primary,
            unfocusedLabelColor = MaterialTheme.colors.primary,
            textColor = MaterialTheme.colors.onSurface
        ),
        label = { Text(text = "item type") },
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(
                expanded = isExpanded
            )
        },
        singleLine = true
    )
}

@Composable
fun ImageUploadButton() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.height(48.dp)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Upload icon")
        Text(
            text = stringResource(R.string.image_button),
            modifier = Modifier.padding(start = 15.dp)
        )
    }
    Text(text = "no image uploaded", color = MaterialTheme.colors.onSurface)
}

@Composable
fun ControlButtons(onCancelClick: () -> Unit, onCreateClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedButton(
            modifier = Modifier
                .weight(1f),
            onClick = { onCancelClick() },
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color.Transparent,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.primary)
        ) {
            Text(stringResource(R.string.cancel))
        }
        Button(
            modifier = Modifier.weight(1f),
            onClick = { onCreateClick() }
        ) {
            Text(stringResource(R.string.next))
        }
    }
}
