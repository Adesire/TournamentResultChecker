package com.lagoscountryclub.squash.lccsquash.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchComponent(search: MutableState<String>, hint: String) {
    OutlinedTextField(
        value = search.value,
        onValueChange = { search.value = it },
        placeholder = { Text(text = hint) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "search"
            )
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
    )
}

@Preview
@Composable
fun SearchComponentPreview() {
    SearchComponent(search = remember { mutableStateOf("") }, hint = "Search Players")
}