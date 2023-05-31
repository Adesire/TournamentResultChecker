package com.lagoscountryclub.squash.lccsquash.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lagoscountryclub.squash.lccsquash.domain.model.Game
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.presentation.composables.GameItem
import com.lagoscountryclub.squash.lccsquash.presentation.composables.ProfileImage
import com.lagoscountryclub.squash.lccsquash.presentation.composables.SwipeRefreshComponent
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.PlayerViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayerDetailsScreen(
    navController: NavController? = null,
    playerId: Long,
    viewModel: PlayerViewModel? = null,
    showPreview: Boolean = false
) {
    if (!showPreview) {
        viewModel?.getPlayer(playerId)
    }
    val player = if (!showPreview) viewModel!!.player.value else dummyPlayers[0]
    val isRefreshing =
        if (showPreview) false else viewModel!!.showLoader.observeAsState(false).value

    SwipeRefreshComponent(
        showPreview = showPreview,
        isRefreshing = isRefreshing,
        OnRefresh = { viewModel?.getPlayer(playerId) }) { pullRefreshState ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                PlayerProfile(
                    navController = navController,
                    player = player,
                    games = if (!showPreview) player.games else dummyGames
                )
            }
        }
    }

}

@Composable
fun Header(navController: NavController?, player: Player) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = { navController?.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(40.dp)
            )
        }
    }
    ProfileImage(160.dp)
    Text(
        text = player.name,
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center
    )

    Divider()
    PlayerTournamentInfo(player)
    Divider()
    Text(
        text = "Games Played",
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun PlayerTournamentInfo(player: Player) {
    PlayerTournamentInfoDetail(title = "Tournament", value = player.tournamentName)
    PlayerTournamentInfoDetail(title = "Points", value = "${player.points}")
    PlayerTournamentInfoDetail(title = "Total Games", value = "${player.gamesPlayed}")
}

@Composable
fun PlayerTournamentInfoDetail(title: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "$title : ",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PlayerProfile(navController: NavController?, player: Player, games: List<Game>) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Header(navController, player)
        }
        items(games) { game ->
            GameItem(game = game) {
                //On Game Clicked
            }
        }
    }
}

@Preview
@Composable
fun PlayerDetailsScreenPreview() {
    PlayerDetailsScreen(playerId = 1L, showPreview = true)
}

private val dummyGames = listOf(
    Game(
        id = 0,
        player1 = Player(id = 1, name = "John Doe"),
        player2 = Player(id = 2, name = "Mike Doe"),
        player1Point = 3,
        player2Point = 1,
        record = "110",
        scores = "11-5, 11-7, nil"
    ),
    Game(
        id = 2,
        player1 = Player(id = 1, name = "John Doe"),
        player2 = Player(id = 2, name = "Mike Doe"),
        player1Point = 3,
        player2Point = 1,
        record = "110",
        scores = "11-5, 11-7, nil"
    )
)