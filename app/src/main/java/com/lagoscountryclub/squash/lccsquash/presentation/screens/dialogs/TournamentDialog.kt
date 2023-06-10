package com.lagoscountryclub.squash.lccsquash.presentation.screens.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lagoscountryclub.squash.lccsquash.R
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament
import com.lagoscountryclub.squash.lccsquash.presentation.composables.TournamentItem
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.TournamentViewModel

@Composable
fun TournamentDialog(
    showPreview: Boolean = false,
    viewModel: TournamentViewModel? = null,
    tournament: (Tournament) -> Unit = {},
    OnDismiss: () -> Unit = {}
) {
    viewModel?.getAll()
    val tournaments = if (!showPreview) viewModel!!.tournaments else dummyTournaments

    Dialog(onDismissRequest = { OnDismiss.invoke() }, DialogProperties()) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(start = 24.dp)
                )
                Image(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    alignment = Alignment.TopEnd,
                    modifier = Modifier.clickable { OnDismiss.invoke() }
                )
            }
            LazyColumn {
                items(tournaments) { item ->
                    TournamentItem(item) {
                        tournament.invoke(it)
                        OnDismiss.invoke()
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun TournamentDialogPreview() {
    TournamentDialog(showPreview = true)
}

val dummyTournaments = listOf(
    Tournament(
        id = 0,
        name = "Ladder 2021",
        rules = listOf("thou shalt A", "thou shalt B", "thou shalt not C")
    ),
    Tournament(id = 1, name = "Ladder 2022"),
    Tournament(id = 2, name = "Ladder 2023")
)
