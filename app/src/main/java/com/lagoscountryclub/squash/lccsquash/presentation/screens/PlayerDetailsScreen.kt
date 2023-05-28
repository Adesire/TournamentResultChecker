package com.lagoscountryclub.squash.lccsquash.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lagoscountryclub.squash.lccsquash.domain.model.Game
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.presentation.composables.GameItem
import com.lagoscountryclub.squash.lccsquash.presentation.composables.ProfileImage
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.PlayerViewModel

@Composable
fun PlayerDetailsScreen(
    playerId: Long,
    viewModel: PlayerViewModel? = null,
    showPreview: Boolean = false
) {
    if (!showPreview) {
        viewModel?.getPlayer(playerId)
    }
    val player = if (!showPreview) viewModel!!.player.value else dummyPlayers[0]

    Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ProfileImage(160.dp)
            Text(
                text = player.name,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )

            Divider()
            PlayerTournamentInfo(player)
            Divider()
            GamesPlayed(
                games = if (!showPreview) player.games else dummyGames
            )
        }
    }

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
fun GamesPlayed(games: List<Game>) {
    Text(
        text = "Games Played",
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
    LazyColumn {
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
    PlayerDetailsScreen(1L, showPreview = true)
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