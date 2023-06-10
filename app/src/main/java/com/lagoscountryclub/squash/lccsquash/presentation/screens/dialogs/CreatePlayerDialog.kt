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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
fun CreatePlayerDialog(
    showPreview: Boolean = false,
    viewModel: AdminViewModel? = hiltViewModel(),
    navController: NavHostController? = null,
    OnDismiss: () -> Unit = {}
) {

    if (!showPreview && viewModel!!.player.id != -1L) {
        OnDismiss.invoke()
        viewModel.resetCreatedPlayer()
    }

    val name = remember { mutableStateOf("") }

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
                    text = stringResource(R.string.create_player),
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
                text = name.value,
                onValueChange = { name.value = it },
                hint = "Player's name",
                enabled = true
            )

            AppButton(modifier = Modifier, text = "Create") {
                viewModel?.createPlayer(name = name.value)
            }


        }
        if (!showPreview) {
            HandleLoadingAndError(viewModels = arrayOf(viewModel!!), showToast = true)
        }
    }
}

@Preview
@Composable
fun CreatePlayerDialogPreview() {
    CreatePlayerDialog(showPreview = true, viewModel = null)
}
