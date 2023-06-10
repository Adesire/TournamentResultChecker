package com.lagoscountryclub.squash.lccsquash.presentation.composables

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppButton(modifier: Modifier = Modifier, text: String = "", OnClick: () -> Unit = {}) {
    Button(
        modifier = modifier, onClick = OnClick
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
fun AppButtonPreview() {
    AppButton(text = "Sample")
}