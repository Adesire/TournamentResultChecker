package com.lagoscountryclub.squash.lccsquash.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SnackBarComponent(message: String = "", OnActionClick: () -> Unit = {}) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val snackbarHostState = scaffoldState.snackbarHostState

    Scaffold(scaffoldState = scaffoldState, snackbarHost = { SnackbarHost(hostState = snackbarHostState)}) {
        Box(modifier = Modifier.padding(it)) {
            LaunchedEffect(key1 = 1, block = {
                coroutineScope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = "OK",
                        duration = SnackbarDuration.Indefinite
                    )
                    when (result) {
                        SnackbarResult.Dismissed -> {}
                        SnackbarResult.ActionPerformed -> {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            OnActionClick.invoke()
                        }
                    }
                }
            })
        }
    }
}

@Preview
@Composable
fun SnackBarComponentPreview() {
    SnackBarComponent()
}