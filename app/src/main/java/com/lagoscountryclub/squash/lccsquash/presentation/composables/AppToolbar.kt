package com.lagoscountryclub.squash.lccsquash.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lagoscountryclub.squash.lccsquash.ui.theme.Purple500

@Composable
fun AppToolbar(
    title: String,
    showBack: Boolean = false,
    OnTitlePressed: () -> Unit = {},
    OnBackPressed: () -> Unit = {},
    OnMorePressed: () -> Unit = {},
    OnMoreItemPressed: (item: MenuItem) -> Unit = {}
) {
    val menuShowing = remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.clickable(onClick = OnTitlePressed)
                    )
                }
            }
        },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = OnBackPressed) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        },
        actions = {
            Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.wrapContentSize()) {
                IconButton(onClick = {
                    OnMorePressed.invoke()
                    menuShowing.value = !menuShowing.value
                }, modifier = Modifier) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color.White
                    )
                }

                DropdownMenu(
                    expanded = menuShowing.value,
                    onDismissRequest = { menuShowing.value = false }) {
                    MenuItemText(item = MenuItem.AllTournaments) {
                        OnTitlePressed.invoke()
                        menuShowing.value = false
                    }
                    MenuItemText(item = MenuItem.AdminView) {
                        OnMoreItemPressed.invoke(it)
                    }
                }
            }
        }
    )
}

@Composable
fun MenuItemText(item: MenuItem, OnClick: (item: MenuItem) -> Unit) {
    if (item.isVisible) {
        Text(
            text = item.title, modifier = Modifier
                .clickable(onClick = { OnClick.invoke(item) })
                .padding(8.dp)
        )
    }
}

sealed class MenuItem(val title: String, val isVisible: Boolean = true) {
    object AllTournaments : MenuItem("All Tournaments")
    object AdminView : MenuItem("Admin")
}


@Composable
fun CustomAppBar(
    title: String,
    OnTitlePressed: () -> Unit = {},
    OnBackPressed: () -> Unit = {},
    OnMorePressed: () -> Unit = {}
) {

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier.background(Purple500)
    ) {
        IconButton(onClick = OnBackPressed) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.clickable(onClick = OnTitlePressed)
                )
            }
        }

        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = OnMorePressed, modifier = Modifier) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = Color.White
                )
            }
        }

    }
}

@Preview
@Composable
fun AppToolbarPreview() {
    AppToolbar(title = "Tournament")
}