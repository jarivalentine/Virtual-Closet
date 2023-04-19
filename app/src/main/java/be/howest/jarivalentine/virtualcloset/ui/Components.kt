package be.howest.jarivalentine.virtualcloset.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import be.howest.jarivalentine.virtualcloset.R

@Composable
fun InputField(text: String, textChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = { textChange(it) },
        modifier = Modifier
            .fillMaxWidth(),
/*        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Black,
            focusedIndicatorColor = Color.Gray,
            cursorColor = Color.White,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Gray
        ),*/
        label = { Text(text = stringResource(R.string.text_name)) },
        singleLine = true
    )
}