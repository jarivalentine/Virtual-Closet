package be.howest.jarivalentine.virtualcloset.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.howest.jarivalentine.virtualcloset.R
import kotlinx.coroutines.launch

@Composable
fun CreateScreen(
    viewModel: VirtualClosetViewModel,
    onCancelClick: () -> Unit,
    onCreateClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val coroutineScope = rememberCoroutineScope()
        ItemNameTextField(itemUiState = viewModel.itemUiState, onValueChange = viewModel::updateItemUiState)
        ItemTypeDropdown(itemUiState = viewModel.itemUiState, onValueChange = viewModel::updateItemUiState)
        ImageUploadButton()
        ControlButtons(
            onCreateClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    onCreateClick()
                }
            },
            onCancelClick = {
                onCancelClick()
            },
            isActive = viewModel.itemUiState.actionEnabled
        )
    }
}

@Composable
fun ItemNameTextField(itemUiState: ItemUiState, onValueChange: (ItemUiState) -> Unit) {
    OutlinedTextField(
        value = itemUiState.name,
        onValueChange = { onValueChange(itemUiState.copy(name = it)) },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = MaterialTheme.colors.primary,
            unfocusedLabelColor = MaterialTheme.colors.primary,
            textColor = MaterialTheme.colors.onSurface
        ),
        label = { Text(text = stringResource(R.string.text_name)) },
        singleLine = true
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemTypeDropdown(itemUiState: ItemUiState, onValueChange: (ItemUiState) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        DropdownTextField(itemUiState.type, expanded)
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            tags.forEach { selected ->
                DropdownMenuItem(onClick = {
                    onValueChange(itemUiState.copy(type = selected))
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
fun ControlButtons(
    onCancelClick: () -> Unit,
    onCreateClick: () -> Unit,
    isActive: Boolean
) {
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
            onClick = { onCreateClick() },
            enabled = isActive
        ) {
            Text(stringResource(R.string.next))
        }
    }
}
