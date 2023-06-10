package com.lagoscountryclub.squash.lccsquash.presentation.screens.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lagoscountryclub.squash.lccsquash.R
import com.lagoscountryclub.squash.lccsquash.presentation.HandleLoadingAndError
import com.lagoscountryclub.squash.lccsquash.presentation.composables.AppButton
import com.lagoscountryclub.squash.lccsquash.presentation.composables.AppTextField
import com.lagoscountryclub.squash.lccsquash.presentation.composables.DropDownComponent
import com.lagoscountryclub.squash.lccsquash.presentation.screens.dummyPlayers
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.AdminViewModel
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.PlayerViewModel

@Composable
fun CreateGameDialog(
    showPreview: Boolean = false,
    playerViewModel: PlayerViewModel? = null,
    viewModel: AdminViewModel? = hiltViewModel(),
    navController: NavHostController? = null,
    OnDismiss: () -> Unit = {}
) {

    if (!showPreview && viewModel!!.game.id != -1L) {
        OnDismiss.invoke()
        viewModel.resetCreatedGame()
    }

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
                    text = stringResource(R.string.create_game),
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

            PlayerDropDown(
                showPreview = showPreview,
                isPlayer1 = true,
                playerViewModel = playerViewModel,
                viewModel = viewModel,
                defaultSelect = viewModel!!.game.player1.name,
                points = viewModel.game.player1Point.toString()
            )
            Text(
                text = "vs",
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 8.dp)
            )

            PlayerDropDown(
                showPreview = showPreview,
                isPlayer1 = false,
                playerViewModel = playerViewModel,
                viewModel = viewModel,
                defaultSelect = viewModel.game.player2.name,
                points = viewModel.game.player2Point.toString()
            )

            ScoreInformation(viewModel = viewModel)

            AppButton(modifier = Modifier, text = "Create") {
                viewModel.createGame()
            }

        }
        if (!showPreview) {
            HandleLoadingAndError(viewModels = arrayOf(viewModel!!), showToast = true)
        }
    }
}

@Composable
fun PlayerDropDown(
    showPreview: Boolean = false,
    isPlayer1: Boolean,
    playerViewModel: PlayerViewModel?,
    viewModel: AdminViewModel?,
    defaultSelect: String,
    points: String
) {
    val players = if (showPreview) dummyPlayers else playerViewModel?.players.orEmpty()
    val preload =
        if (showPreview) false else playerViewModel?.showLoader?.observeAsState(false)?.value
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DropDownComponent(
            dataList = players,
            onDataSelect = {
                viewModel?.updatePlayer(it, isPlayer1)
                it.name
            },
            hint = "Select Player",
            itemView = {
                Text(text = it.name)
            },
            defaultSelect = if (showPreview) "" else defaultSelect,
            onPreLoad = preload ?: false,
            modifier = Modifier.padding(horizontal = 16.dp),
            textBoxEnabled = false,
            filterCondition = { item, text ->
                item.name.startsWith(text, true)
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            AppTextField(
                modifier = Modifier.width(100.dp),
                text = if (showPreview) "" else points,
                onValueChange = {
                    viewModel?.updatePlayerPoints(it.ifEmpty { "0" }.toInt(), isPlayer1)
                },
                hint = "Point(s)",
                textSize = 18.sp,
                keyboardType = KeyboardType.Number,
                enabled = true,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ScoreInformation(bestOfCount: Int = 3, viewModel: AdminViewModel?) {
    val rounds = viewModel?.rounds
    viewModel?.updateRoundsData(bestOfCount)
    Text(
        text = "Round Records",
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .wrapContentSize()
            .padding(vertical = 8.dp)
    )
    var count = 0

    rounds!!.forEachIndexed { index, round ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Round ${index + 1}:",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 8.dp)
            )

            AppTextField(
                modifier = Modifier.width(100.dp),
                text = round.player1Score.toString(),
                onValueChange = {
                    val result = it.ifEmpty { "0" }
                    round.player1Score = result.toInt()
                },
                hint = "P1 score",
                textSize = 12.sp,
                keyboardType = KeyboardType.Number,
                enabled = true
            )

            Text(
                text = "-",
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 8.dp)
            )

            AppTextField(
                modifier = Modifier.width(100.dp),
                text = round.player2Score.toString(),
                onValueChange = {
                    val result = it.ifEmpty { "0" }
                    round.player2Score = result.toInt()
                },
                hint = "P2 score",
                textSize = 12.sp,
                keyboardType = KeyboardType.Number,
                enabled = true
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun CreateGameDialogPreview() {
    CreateGameDialog(showPreview = true, viewModel = null)
}
