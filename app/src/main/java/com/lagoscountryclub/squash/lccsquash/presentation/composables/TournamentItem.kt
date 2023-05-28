package com.lagoscountryclub.squash.lccsquash.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TournamentItem(tournament: Tournament, OnClick: (tournament: Tournament) -> Unit = {}) {
    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Card(
            shape = RoundedCornerShape(8.dp),
            onClick = { OnClick.invoke(tournament) },
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            elevation = 2.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = tournament.name,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun TournamentItemPreview() {
    TournamentItem(Tournament(id = 0, name = "Ladder 2023"))
}