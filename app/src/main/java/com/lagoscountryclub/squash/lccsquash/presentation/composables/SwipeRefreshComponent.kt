package com.lagoscountryclub.squash.lccsquash.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeRefreshComponent(
    showPreview: Boolean = false,
    isRefreshing: Boolean = false,
    OnRefresh: () -> Unit = {},
    content: @Composable (PullRefreshState) -> Unit = {}
) {

    val pullRefreshState = rememberPullRefreshState(isRefreshing, OnRefresh)

    Box(
        Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        content.invoke(pullRefreshState)
        if (!showPreview) {
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun SwipeRefreshComponentPreview() {
    SwipeRefreshComponent()
}