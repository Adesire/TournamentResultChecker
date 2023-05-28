package com.lagoscountryclub.squash.lccsquash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lagoscountryclub.squash.lccsquash.presentation.MainScreen
import com.lagoscountryclub.squash.lccsquash.presentation.composables.AppToolbar
import com.lagoscountryclub.squash.lccsquash.ui.theme.LccSquashTournamentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LccSquashTournamentTheme {
                MainApp(getString(R.string.app_name))
            }
        }
    }
}

@Composable
fun MainApp(appTitle: String = "") {
    val showToolbar = remember { mutableStateOf(true) }
    val title = remember {
        mutableStateOf(appTitle)
    }
    val onTitlePressed = remember {
        mutableStateOf(false)
    }
    Scaffold(topBar = {
        if (showToolbar.value) {
            AppToolbar(title = title.value, OnTitlePressed = {
                onTitlePressed.value = true
            })
        }
    }) {// A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            MainScreen(showToolbar = { show ->
                showToolbar.value = show
            }, onTitlePressed = onTitlePressed, tournament = { tournament ->
                title.value = tournament.name
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LccSquashTournamentTheme {
        MainApp()
    }
}