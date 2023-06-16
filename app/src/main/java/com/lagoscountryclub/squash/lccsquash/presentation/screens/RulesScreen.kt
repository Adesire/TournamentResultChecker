package com.lagoscountryclub.squash.lccsquash.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lagoscountryclub.squash.lccsquash.R
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament
import com.lagoscountryclub.squash.lccsquash.presentation.HandleLoadingAndError
import com.lagoscountryclub.squash.lccsquash.presentation.composables.RichTextStyleRow
import com.lagoscountryclub.squash.lccsquash.presentation.composables.SwipeRefreshComponent
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.TournamentViewModel
import com.mohamedrejeb.richeditor.model.RichTextValue
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RulesScreen(
    viewModel: TournamentViewModel? = null,
    showPreview: Boolean = false,
    tournament: Tournament? = null
) {
    viewModel?.showAdminViews()
    val rules = if (!showPreview) viewModel!!.tournament else remember {
        mutableStateOf(com.lagoscountryclub.squash.lccsquash.presentation.screens.dialogs.dummyTournaments[0])
    }

    val isRefreshing =
        if (showPreview) false else viewModel!!.showLoader.observeAsState(false).value

    val showAdminActions = showPreview || viewModel!!.isAdmin.observeAsState(false).value


    if (!showPreview && viewModel!!.updateDone.value) {
        viewModel.getTournament(tournament?.id)
        viewModel.resetUpdate()
    }

    val richTextValue = remember {
        mutableStateOf(
            RichTextValue.from(
                tournament?.rulesContent ?: ""
            )
        )
    }

    SwipeRefreshComponent(
        showPreview = showPreview,
        isRefreshing = isRefreshing,
        OnRefresh = { viewModel?.getTop30() }) { pullRefreshState ->

        Column(modifier = Modifier.pullRefresh(pullRefreshState)) {
            if (showAdminActions) {
                RichTextStyleRow(
                    modifier = Modifier.fillMaxWidth(),
                    value = richTextValue.value,
                    onValueChanged = {
                        richTextValue.value = it
                        viewModel?.updateRulesContent(it.toHtml())
                    },
                )
            }
            RichTextEditor(
                modifier = Modifier.fillMaxSize(),
                value = richTextValue.value,
                enabled = showAdminActions,
                onValueChange = {
                    richTextValue.value = it
                    viewModel?.updateRulesContent(it.toHtml())
                },
            )

            /*LazyColumn {
                item {
                    RulesHeader()
                }
                itemsIndexed(rules.value.rules) { index, item ->
                    RuleItem(position = index, rule = item)
                }

            }*/
        }
        if (showAdminActions) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                ExtendedFloatingActionButton(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            tint = Color.White
                        )
                    },
                    text = { Text(text = "Update", color = Color.White) },
                    onClick = { viewModel?.updateTournament() }
                )
            }
        }
    }

    if (!showPreview) {
        HandleLoadingAndError(viewModels = arrayOf(viewModel!!))
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
