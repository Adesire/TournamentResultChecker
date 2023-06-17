package com.lagoscountryclub.squash.lccsquash.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.presentation.composables.PlayerItem
import com.lagoscountryclub.squash.lccsquash.presentation.composables.SwipeRefreshComponent
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.PlayerViewModel
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.TournamentViewModel
import com.lagoscountryclub.squash.lccsquash.ui.theme.Bronze
import com.lagoscountryclub.squash.lccsquash.ui.theme.Gold
import com.lagoscountryclub.squash.lccsquash.ui.theme.Silver

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LeaderboardScreen(
    navController: NavHostController? = null,
    viewModel: TournamentViewModel? = null,
    playerViewModel: PlayerViewModel? = null,
    showPreview: Boolean = false
) {

    LaunchedEffect(key1 = 1) {
        viewModel?.getTop30()
    }

    val players = if (!showPreview) viewModel!!.players else dummyLeaders

    val isRefreshing =
        if (showPreview) false else viewModel!!.showLoader.observeAsState(false).value

    SwipeRefreshComponent(
        showPreview = showPreview,
        isRefreshing = isRefreshing,
        OnRefresh = { viewModel?.getTop30() }) { pullRefreshState ->

        Column(modifier = Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn {
                item {
                    LeaderboardHeader()
                }
                if (players.isEmpty()) {
                    item { NoLeaderYet() }
                }
                itemsIndexed(players) { index, item ->
                    PlayerItem(player = item, isLeaderboard = true, position = index) { player ->
                        playerViewModel?.resetPlayer()
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

@Composable
fun LeaderboardHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        contentAlignment = Alignment.Center
    ) {

        /*val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.lagoscountryclub.squash.lccsquash.R.raw.leader_board))
        LottieAnimation(
            modifier = Modifier.size(240.dp),
            composition = composition
        )*/

        Row(verticalAlignment = Alignment.Bottom) {
            Podium(color = Bronze, podiumHeight = 50.dp)
            Podium(color = Gold, podiumHeight = 100.dp)
            Podium(color = Silver, podiumHeight = 70.dp)
        }

    }
}

@Composable
fun Podium(color: Color, podiumHeight: Dp) {
    Column {
        Box(
            modifier = Modifier
                .size(width = 50.dp, height = podiumHeight)
                .background(color = color),
            contentAlignment = Alignment.Center
        ) {

        }
        Image(
            imageVector = Icons.Outlined.Star,
            contentDescription = "star",
            modifier = Modifier
                .size(42.dp),
            colorFilter = ColorFilter.tint(color)
        )
    }
}

@Composable
fun NoLeaderYet() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 100.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.lagoscountryclub.squash.lccsquash.R.raw.no_leader))
        LottieAnimation(
            modifier = Modifier.size(240.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}

@Preview
@Composable
fun LeaderboardScreenPreview() {
    LeaderboardScreen(showPreview = true)
}

val dummyLeaders = listOf(Player(id = 1, name = "John Doe"), Player(id = 2, name = "Mike Doe"))