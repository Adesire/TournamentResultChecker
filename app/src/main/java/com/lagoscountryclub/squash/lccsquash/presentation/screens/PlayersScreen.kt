package com.lagoscountryclub.squash.lccsquash.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.presentation.composables.PlayerItem
import com.lagoscountryclub.squash.lccsquash.presentation.composables.SearchComponent
import com.lagoscountryclub.squash.lccsquash.presentation.composables.SwipeRefreshComponent
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.PlayerViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayersScreen(
    navController: NavHostController? = null,
    viewModel: PlayerViewModel? = null,
    showPreview: Boolean = false
) {
    var players = if (!showPreview) viewModel!!.players else dummyPlayers
    val search = remember { mutableStateOf("") }

    if(players.isEmpty()) {
        viewModel?.getAllPlayers()
    }

    if (search.value.isNotEmpty()) {
        viewModel?.filterPlayers(search.value)
        players = viewModel!!.playersFiltered
    }

    val isRefreshing =
        if (showPreview) false else viewModel!!.showLoader.observeAsState(false).value

    SwipeRefreshComponent(
        showPreview = showPreview,
        isRefreshing = isRefreshing,
        OnRefresh = { viewModel?.getAllPlayers() }) { pullRefreshState ->

        Column(modifier = Modifier.pullRefresh(pullRefreshState)) {
            SearchComponent(search = search, hint = "Search Players")

            LazyColumn {
                items(players) {
                    PlayerItem(player = it) { player ->
                        navController?.navigate(
                            NavRoutes.PLAYER_DETAILS.replace(
                                "{playerId}",
                                "${player.id}"
                            )
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun PlayersScreenPreview() {
    PlayersScreen(showPreview = true)
}

val dummyPlayers = listOf(Player(id = 1, name = "John Doe"), Player(id = 2, name = "Mike Doe"))