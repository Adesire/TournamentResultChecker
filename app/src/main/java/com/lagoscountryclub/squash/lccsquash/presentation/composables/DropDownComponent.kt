package com.lagoscountryclub.squash.lccsquash.presentation.composables

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> DropDownComponent(
    modifier: Modifier = Modifier,
    dataList: List<T> = emptyList(),
    onDataSelect: (T) -> Unit = {},
    itemView: @Composable (T) -> Unit = {},
    defaultSelect: String = "",
    hint: String = "",
    onPreLoad: Boolean = false,
    textBoxEnabled: Boolean = false,
    filterCondition: (item: T, text: String) -> Boolean = { _, _ -> false }
) {

    var mExpanded by remember { mutableStateOf(false) }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var textInput by remember {
        mutableStateOf("")
    }

    val list = dataList.filter {
        filterCondition.invoke(it, textInput)
    }.ifEmpty { dataList }.toMutableList()

    ExposedDropdownMenuBox(expanded = mExpanded, onExpandedChange = {
        mExpanded = !mExpanded
    }) {

        AppTextField(
            modifier = modifier.clickable { mExpanded = !mExpanded },
            text = if (textBoxEnabled) textInput else defaultSelect,
            onValueChange = {
                textInput = it
                if (it.isNotEmpty()) {
                    mExpanded = true
                }
            },
            trailingIcon = {
                if (onPreLoad) {
                    val infiniteTransition = rememberInfiniteTransition()
                    val angle by infiniteTransition.animateFloat(
                        initialValue = 0F,
                        targetValue = 360F,
                        animationSpec = infiniteRepeatable(
                            animation = tween(2000, easing = LinearEasing)
                        )
                    )
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Loading",
                        modifier = Modifier.rotate(angle)
                    )
                } else {
                    Icon(
                        icon, "contentDescription",
                        Modifier.clickable { mExpanded = !mExpanded }
                    )
                }
            },
            hint = defaultSelect.ifEmpty { hint },
            enabled = true,
            readOnly = !textBoxEnabled
        )

        if (!textBoxEnabled) {
            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = modifier
                    .fillMaxWidth(0.8f)
            ) {
                list.forEach { label ->
                    DropdownMenuItem(onClick = {
                        onDataSelect(label)
                        mExpanded = false
                    }) {
                        itemView(label)
                    }
                }
            }
        } else {
            ExposedDropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = modifier
                    .fillMaxWidth()
            ) {

                list.forEach { label ->
                    DropdownMenuItem(onClick = {
                        onDataSelect(label)
                        mExpanded = false
                    }) {
                        itemView(label)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DropDownComponentPreview() {
    DropDownComponent<Any>()
}
