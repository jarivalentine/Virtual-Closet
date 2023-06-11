package be.howest.jarivalentine.virtualcloset.ui.screens

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
import be.howest.jarivalentine.virtualcloset.ui.BrandUiState
import be.howest.jarivalentine.virtualcloset.ui.VirtualClosetViewModel

@Composable
fun CreateScreen(
    onCancelClick: () -> Unit,
    onCreateClick: () -> Unit,
    onNameValueChange: (String) -> Unit,
    onTypeValueChange: (String) -> Unit,
    onImageChange: (String) -> Unit,
    name: String,
    type: String,
    brand: String?,
    isActive: Boolean,
    viewModel: VirtualClosetViewModel?,
    onBrandValueChange: ((String, String?) -> Unit)?
) {
    val case = if (viewModel != null) "item" else "outfit"
    var showCamera by remember { mutableStateOf(false) }
    if (showCamera) {
        CameraScreen(exitCamera = { showCamera = false }, onImageChange = onImageChange)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            NameTextField(name = name, onValueChange = onNameValueChange, case = case)
            if (viewModel != null && onBrandValueChange != null && brand != null) {
                val brandUiState = viewModel.brandUiState
                BrandDropdown(
                    brandName = brand,
                    onValueChange = onBrandValueChange,
                    brandUiState = brandUiState,
                    case = case
                )
                TypeDropdown(type = type, onValueChange = onTypeValueChange, case = case, types = tags)
            } else {
                TypeDropdown(type = type, onValueChange = onTypeValueChange, case = case, types = labels)
            }
            Row {
                TakeImageButton(onClick = {
                    showCamera = true
                })
            }
            ControlButtons(
                onCreateClick = {
                    onCreateClick()
                },
                onCancelClick = {
                    onCancelClick()
                },
                isActive = isActive
            )
        }
    }
}

@Composable
fun TakeImageButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.height(48.dp)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Upload icon")
        Text(
            text = stringResource(R.string.image_button),
            modifier = Modifier.padding(start = 15.dp)
        )
    }
}

@Composable
fun NameTextField(name: String, onValueChange: (String) -> Unit, case: String) {
    OutlinedTextField(
        value = name,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = MaterialTheme.colors.primary,
            unfocusedLabelColor = MaterialTheme.colors.primary,
            textColor = MaterialTheme.colors.onSurface
        ),
        label = { Text(text = "$case name") },
        singleLine = true
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TypeDropdown(type: String, onValueChange: (String) -> Unit, case: String, types: List<String>) {
    var expanded by remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        DropdownTextField(type, expanded, "$case type")
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            types.forEach { selected ->
                DropdownMenuItem(onClick = {
                    onValueChange(selected)
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
fun BrandDropdown(
    brandName: String,
    onValueChange: (String, String?) -> Unit,
    brandUiState: BrandUiState,
    case: String
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        DropdownTextField(brandName, expanded, label = "$case brand")
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            when (brandUiState) {
                is BrandUiState.Loading -> {
                    DropdownMenuItem(onClick = {}) {
                        Text(text = "Loading...")
                    }
                }

                is BrandUiState.Success -> {
                    brandUiState.brands.forEach { brand ->
                        DropdownMenuItem(onClick = {
                            onValueChange(brand.brandName, brand.brandLogoUrl ?: "")
                            expanded = false
                        }) {
                            Text(text = brand.brandName)
                        }
                    }
                }

                is BrandUiState.Error -> {
                    DropdownMenuItem(onClick = {}) {
                        Text(text = "Error")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownTextField(selectedItem: String, isExpanded: Boolean, label: String) {
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
        label = { Text(text = label) },
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(
                expanded = isExpanded
            )
        },
        singleLine = true
    )
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
