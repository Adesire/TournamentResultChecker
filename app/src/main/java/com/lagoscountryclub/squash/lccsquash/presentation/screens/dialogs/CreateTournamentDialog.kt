package com.lagoscountryclub.squash.lccsquash.presentation.screens.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lagoscountryclub.squash.lccsquash.R
import com.lagoscountryclub.squash.lccsquash.presentation.HandleLoadingAndError
import com.lagoscountryclub.squash.lccsquash.presentation.composables.AppButton
import com.lagoscountryclub.squash.lccsquash.presentation.composables.AppTextField
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.AdminViewModel

@Composable
fun CreateTournamentDialog(
    showPreview: Boolean = false,
    viewModel: AdminViewModel? = hiltViewModel(),
    navController: NavHostController? = null,
    OnDismiss: () -> Unit = {}
) {

    if (!showPreview && viewModel!!.createDone.value) {
        OnDismiss.invoke()
        viewModel.resetCreate()
        viewModel.resetCreatedTournament()
    }
    if (!showPreview && viewModel!!.tournament.id != -1L) {
        viewModel.resetCreatedTournament()
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
                    text = stringResource(R.string.create_tournament),
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

            AppTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = if (showPreview) "" else viewModel!!.tournament.name,
                onValueChange = { viewModel?.updateTournamentName(it) },
                hint = "Name",
                enabled = true
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                AppTextField(
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(100.dp)
                        .padding(vertical = 16.dp),
                    text = if (showPreview) "" else viewModel!!.tournament.year,
                    onValueChange = { viewModel?.updateTournamentYear(it) },
                    hint = "Year",
                    textAlign = TextAlign.Center,
                    keyboardType = KeyboardType.Number,
                    enabled = true
                )
            }

            BestOfCount {
                viewModel?.updateTournamentBestOfCount(it)
            }

            AppButton(modifier = Modifier, text = "Create") {
                viewModel?.createTournament()
            }

        }
        if (!showPreview) {
            HandleLoadingAndError(viewModels = arrayOf(viewModel!!), showToast = true)
        }
    }
}

@Composable
fun BestOfCount(count: (String) -> Unit) {
    val text = remember { mutableStateOf("0") }
    Text(
        modifier = Modifier.padding(8.dp),
        text = "Best Of Count",
        style = MaterialTheme.typography.subtitle1
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(onClick = {
            var value = text.value.toInt()
            if (value > 0) {
                value -= 1
                val result = value.toString()
                text.value = result
                count.invoke(result)
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_horizontal_rule_24),
                contentDescription = null
            )
        }

        Text(text = text.value, textAlign = TextAlign.Center)

        IconButton(onClick = {
            var value = text.value.toInt()
            if (value < 7) {
                value += 1
                val result = value.toString()
                text.value = result
                count.invoke(result)
            }
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }

}

@Preview
@Composable
fun CreateTournamentDialogPreview() {
    CreateTournamentDialog(showPreview = true, viewModel = null)
}
