package com.lagoscountryclub.squash.lccsquash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament
import com.lagoscountryclub.squash.lccsquash.presentation.composables.SnackBarComponent
import com.lagoscountryclub.squash.lccsquash.presentation.screens.*
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.PlayerViewModel
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.TournamentViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    showToolbar: (Boolean) -> Unit = {},
    showBottomNav: (Boolean) -> Unit = {},
    onTitlePressed: MutableState<Boolean>,
    tournament: (Tournament) -> Unit = {}
) {
    val playerViewModel: PlayerViewModel = hiltViewModel()
    val tournamentViewModel: TournamentViewModel = hiltViewModel()

    lateinit var OnRefreshAction: () -> Unit

    val showDialog = remember {
        onTitlePressed
    }
    var t: Tournament? = null

    if (showDialog.value) {
        TournamentDialog(
            viewModel = tournamentViewModel,
            tournament = {
                t = it
                playerViewModel.selectedTournament(it)
                tournament.invoke(it)
            },
            OnDismiss = {
                showDialog.value = false
            }
        )
    }

    HandleLoadingAndError(
        playerViewModel = playerViewModel,
        tournamentViewModel = tournamentViewModel
    )

    NavHost(navController = navController, startDestination = NavRoutes.HOME) {
        composable(route = NavRoutes.HOME) {
            showToolbar.invoke(true)
            showBottomNav.invoke(true)
            PlayersScreen(navController, playerViewModel)
        }

        composable(route = NavRoutes.LEADERBOARD) {
            showToolbar.invoke(false)
            showBottomNav.invoke(true)
            LeaderboardScreen(navController, tournamentViewModel)
        }

        composable(route = NavRoutes.RULES) {
            showToolbar.invoke(true)
            showBottomNav.invoke(true)
            RulesScreen(tournament = t, viewModel = tournamentViewModel)
        }

        composable(
            route = NavRoutes.PLAYER_DETAILS,
            arguments = listOf(navArgument("playerId") {
                type = NavType.LongType
            })
        ) {
            showToolbar.invoke(false)
            showBottomNav.invoke(false)
            val playerId = it.arguments!!.getLong("playerId")
            PlayerDetailsScreen(navController, playerId, playerViewModel)
        }
    }
}

@Composable
fun HandleLoadingAndError(
    playerViewModel: PlayerViewModel,
    tournamentViewModel: TournamentViewModel,
    OnRefreshAction:() -> Unit = {}
) {
    val loadingMediator = MediatorLiveData<Boolean>().apply {
        addSource(playerViewModel.showLoader) { value = it }
        addSource(tournamentViewModel.showLoader) { value = it }
    }
    val loading = loadingMediator.observeAsState(false)

    if (loading.value) {
        LoadingIndicator()
    }

    val errorMediator = MediatorLiveData<String>().apply {
        addSource(playerViewModel.showError) { value = it }
        addSource(tournamentViewModel.showError) { value = it }
    }

    val error = errorMediator.observeAsState(initial = "")
    if (error.value.isNotEmpty()) {
        SnackBarComponent(error.value) {
            playerViewModel.setErrorMessage("")
            tournamentViewModel.setErrorMessage("")
            OnRefreshAction.invoke()
        }
    }
}