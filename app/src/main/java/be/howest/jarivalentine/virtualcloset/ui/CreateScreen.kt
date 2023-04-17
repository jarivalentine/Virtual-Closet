package be.howest.jarivalentine.virtualcloset.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.howest.jarivalentine.virtualcloset.R
import be.howest.jarivalentine.virtualcloset.data.tags
import be.howest.jarivalentine.virtualcloset.ui.theme.VirtualClosetTheme

@Composable
fun CreateScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ItemNameTextField()
        ItemTypeDropdown()
        ImageUploadButton()
        ControlButtons()
    }
}

@Composable
fun ItemNameTextField() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth(),
/*        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Black,
            focusedIndicatorColor = Color.Gray,
            cursorColor = Color.White,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Gray
        ),*/
        label = { Text(text = "item name") },
        singleLine = true
    )
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
    TextField(
        value = selectedItem,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth(),
        readOnly = true,
/*        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Black,
            focusedIndicatorColor = Color.Gray,
            cursorColor = Color.White,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Gray
        ),*/
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
/*        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black,
            contentColor = Color.White
        ),*/
        modifier = Modifier.height(48.dp)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Upload icon")
        Text(
            text = stringResource(R.string.image_button),
            modifier = Modifier.padding(start = 15.dp)
        )
    }
    Text(text = "no image uploaded")
}

@Composable
fun ControlButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedButton(modifier = Modifier.weight(1f), onClick = { }) {
            Text(stringResource(R.string.cancel))
        }
        Button(
            modifier = Modifier.weight(1f),
            onClick = {  }
        ) {
            Text(stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VirtualClosetTheme {
        CreateScreen()
    }
}
