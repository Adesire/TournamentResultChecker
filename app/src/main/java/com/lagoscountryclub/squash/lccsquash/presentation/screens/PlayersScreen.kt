package com.lagoscountryclub.squash.lccsquash.presentation.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lagoscountryclub.squash.lccsquash.R.*
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.presentation.composables.PlayerItem
import com.lagoscountryclub.squash.lccsquash.presentation.composables.SearchComponent
import com.lagoscountryclub.squash.lccsquash.presentation.composables.SwipeRefreshComponent
import com.lagoscountryclub.squash.lccsquash.presentation.screens.dialogs.CreateGameDialog
import com.lagoscountryclub.squash.lccsquash.presentation.screens.dialogs.CreatePlayerDialog
import com.lagoscountryclub.squash.lccsquash.presentation.screens.dialogs.CreateTournamentDialog
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.PlayerViewModel
import com.leinardi.android.speeddial.compose.FabWithLabel
import com.leinardi.android.speeddial.compose.SpeedDial
import com.leinardi.android.speeddial.compose.SpeedDialOverlay
import com.leinardi.android.speeddial.compose.SpeedDialState

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun PlayersScreen(
    navController: NavHostController? = null,
    viewModel: PlayerViewModel? = null,
    showPreview: Boolean = false
) {
    val speedDialState = rememberSaveable { mutableStateOf(SpeedDialState.Collapsed) }
    val overlayVisible = rememberSaveable { mutableStateOf(speedDialState.value.isExpanded()) }


    var players = if (!showPreview) viewModel!!.players else dummyPlayers
    val search = remember { mutableStateOf("") }

    if (players.isEmpty()) {
        viewModel?.getAllPlayers()
    }
    viewModel?.showAdminViews()

    if (search.value.isNotEmpty()) {
        viewModel?.filterPlayers(search.value)
        players = viewModel!!.playersFiltered
    }
    val showAdminActions = showPreview || viewModel!!.isAdmin.observeAsState(false).value

    val isRefreshing =
        if (showPreview) false else viewModel!!.showLoader.observeAsState(false).value

    SwipeRefreshComponent(
        showPreview = showPreview,
        isRefreshing = isRefreshing,
        OnRefresh = { viewModel?.getAllPlayers() }) { pullRefreshState ->

        SpeedDialOverlay(
            visible = overlayVisible.value,
            onClick = {
                overlayVisible.value = false
                speedDialState.value = speedDialState.value.toggle()
            },
        )

        Column(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
        ) {
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

        if (showAdminActions) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                SpeedDial(
                    state = speedDialState.value,
                    onFabClick = { expanded ->
                        overlayVisible.value = !expanded
                        speedDialState.value =
                            if (overlayVisible.value) SpeedDialState.Expanded else SpeedDialState.Collapsed
                    },
                    fabClosedContentColor = MaterialTheme.colors.onPrimary,
                    fabOpenedContentColor = MaterialTheme.colors.onPrimary
                ) {
                    item {
                        val showDialog = remember { mutableStateOf(false) }
                        if (showDialog.value) {
                            CreatePlayerDialog {
                                showDialog.value = false
                                viewModel?.getAllPlayers()
                            }
                        }
                        FloatingActionItem(
                            title = string.create_player,
                            drawable = drawable.baseline_person_add_24
                        ) {
                            showDialog.value = true
                        }
                    }
                    item {
                        val showDialog = remember { mutableStateOf(false) }
                        if (showDialog.value) {
                            CreateTournamentDialog {
                                showDialog.value = false
                                viewModel?.getAllPlayers()
                            }
                        }
                        FloatingActionItem(
                            title = string.create_tournament,
                            drawable = drawable.baseline_sports_24
                        ) {
                            showDialog.value = true
                        }
                    }
                    item {
                        val showDialog = remember { mutableStateOf(false) }
                        if (showDialog.value) {
                            CreateGameDialog(playerViewModel = viewModel) {
                                showDialog.value = false
                                viewModel?.getAllPlayers()
                            }
                        }
                        FloatingActionItem(
                            title = string.create_game,
                            drawable = drawable.baseline_games_24
                        ) {
                            showDialog.value = true
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FloatingActionItem(
    @StringRes title: Int,
    imageVector: ImageVector? = null,
    @DrawableRes drawable: Int? = null,
    OnClick: () -> Unit
) {
    FabWithLabel(
        onClick = OnClick,
        labelContent = { Text(text = stringResource(title)) },
    ) {
        imageVector?.let { Icon(imageVector, null) }
        drawable?.let { Icon(painter = painterResource(id = drawable), null) }

    }
}

@Preview
@Composable
fun PlayersScreenPreview() {
    PlayersScreen(showPreview = true)
}

val dummyPlayers = listOf(Player(id = 1, name = "John Doe"), Player(id = 2, name = "Mike Doe"))