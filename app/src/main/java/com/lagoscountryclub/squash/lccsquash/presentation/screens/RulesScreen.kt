package com.lagoscountryclub.squash.lccsquash.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lagoscountryclub.squash.lccsquash.R
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament
import com.lagoscountryclub.squash.lccsquash.presentation.composables.SwipeRefreshComponent
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.TournamentViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RulesScreen(
    viewModel: TournamentViewModel? = null,
    showPreview: Boolean = false,
    tournament: Tournament? = null
) {
    viewModel?.getTournament(tournament?.id)
    val rules = if (!showPreview) viewModel!!.tournament else remember {
        mutableStateOf(com.lagoscountryclub.squash.lccsquash.presentation.screens.dialogs.dummyTournaments[0])
    }

    val isRefreshing =
        if (showPreview) false else viewModel!!.showLoader.observeAsState(false).value

    SwipeRefreshComponent(
        showPreview = showPreview,
        isRefreshing = isRefreshing,
        OnRefresh = { viewModel?.getTop30() }) { pullRefreshState ->

        Column(modifier = Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn {
                item {
                    RulesHeader()
                }
                itemsIndexed(rules.value.rules) { index, item ->
                    RuleItem(position = index, rule = item)
                }

            }
        }
    }
}

@Composable
fun RuleItem(position: Int, rule: String) {
    Box(modifier = Modifier.padding(8.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "${position + 1}",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = rule,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun RulesHeader() {
    Text(
        text = stringResource(R.string.app_name).plus(" Rules"),
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(start = 24.dp)
    )
}

@Preview
@Composable
fun RulesScreenPreview() {
    RulesScreen(showPreview = true)
}
