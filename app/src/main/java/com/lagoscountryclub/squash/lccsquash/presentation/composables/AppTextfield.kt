package com.lagoscountryclub.squash.lccsquash.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    hint: String = "",
    textAlign: TextAlign = TextAlign.Start,
    textSize: TextUnit = TextUnit.Unspecified,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false
) {
    val input = remember {
        mutableStateOf(text)
    }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        readOnly = readOnly,
        value = input.value,
        onValueChange = {
            onValueChange.invoke(it)
            input.value = it
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = hint,
                textAlign = textAlign
            )
        },
        trailingIcon = trailingIcon,
        textStyle = TextStyle(fontSize = textSize),
        enabled = enabled
    )
}

@Preview
@Composable
fun AppTextFieldPreview() {
    AppTextField(hint = "Sample", enabled = true)
}