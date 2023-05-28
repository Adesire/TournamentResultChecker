package com.lagoscountryclub.squash.lccsquash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament
import com.lagoscountryclub.squash.lccsquash.presentation.screens.*
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.PlayerViewModel
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.TournamentViewModel

@Composable
fun MainScreen(
    showToolbar: (Boolean) -> Unit = {},
    onTitlePressed: MutableState<Boolean>,
    tournament: (Tournament) -> Unit = {}
) {
    val navController = rememberNavController()

    val playerViewModel: PlayerViewModel = hiltViewModel()
    val tournamentViewModel: TournamentViewModel = hiltViewModel()

    val showDialog = remember {
        onTitlePressed
    }

    if (showDialog.value) {
        TournamentDialog(
            viewModel = tournamentViewModel,
            tournament = {
                playerViewModel.selectedTournament(it)
                tournament.invoke(it)
            },
            OnDismiss = {
                showDialog.value = false
            }
        )
    }

    val loadingMediator = MediatorLiveData<Boolean>().apply {
        addSource(playerViewModel.showLoader) {}
        addSource(tournamentViewModel.showLoader) {}
    }
    val loading = loadingMediator.observeAsState(false)

    if (loading.value) {
        LoadingIndicator()
    }

    val errorMediator = MediatorLiveData<String>().apply {
        addSource(playerViewModel.showError) {}
        addSource(tournamentViewModel.showError) {}
    }

    val error = errorMediator.observeAsState(initial = "")
    if (error.value.isNotEmpty()) {
        LoadingIndicator()
    }

    NavHost(navController = navController, startDestination = NavRoutes.HOME) {
        composable(route = NavRoutes.HOME) {
            showToolbar.invoke(true)
            PlayersScreen(navController, playerViewModel)
        }

        composable(
            route = NavRoutes.PLAYER_DETAILS,
            arguments = listOf(navArgument("playerId") {
                type = NavType.LongType
            })
        ) {
            showToolbar.invoke(false)
            val playerId = it.arguments!!.getLong("playerId")
            PlayerDetailsScreen(playerId, playerViewModel)
        }
    }
}