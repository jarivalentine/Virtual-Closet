package be.howest.jarivalentine.virtualcloset.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import be.howest.jarivalentine.virtualcloset.R

@Composable
fun InputField(text: String, textChange: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = { textChange(it) },
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