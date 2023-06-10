package com.lagoscountryclub.squash.lccsquash.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lagoscountryclub.squash.lccsquash.domain.model.Game
import com.lagoscountryclub.squash.lccsquash.domain.model.Player

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameItem(game: Game, OnClick: (game: Game) -> Unit = {}) {
    Card(
        onClick = { OnClick.invoke(game) },
        modifier = Modifier
            .wrapContentSize()
            .padding(12.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlayerInfo(game)
            ScoreInfo(game.scores)
        }
    }
}

@Composable
fun PlayerInfo(game: Game) {
    val player1 = game.player1
    val player2 = game.player2
    val p1PointsColour = if (game.player1Point > game.player2Point) Color.Green else Color.Red
    val p2PointsColour = if (game.player2Point > game.player1Point) Color.Green else Color.Red
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SinglePlayerDetails(
            name = player1.name,
            points = game.player1Point,
            colour = p1PointsColour
        )

        Text(
            text = "vs",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 8.dp)
        )

        SinglePlayerDetails(
            name = player2.name,
            points = game.player2Point,
            colour = p2PointsColour
        )
    }
}

@Composable
fun SinglePlayerDetails(name: String, points: Int, colour: Color) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentSize()
        )
        Text(
            text = points.toString(),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentSize(),
            color = colour
        )
    }
}

@Composable
fun ScoreInfo(score: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(36.dp)
    ) {
        Text(
            text = "Scores:",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentSize()
        )

        val list = score.split(",")
        for (item in list) {
            Text(
                text = item,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}

@Preview
@Composable
fun GameItemPreview() {
    GameItem(
        Game(
            id = 0,
            player1 = Player(id = 1, name = "John Doe"),
            player2 = Player(id = 2, name = "Mike Doe"),
            player1Point = 3,
            player2Point = 1,
            record = "110",
            scores = "11-5, 11-7, nil"
        )
    )
}