package com.lagoscountryclub.squash.lccsquash.presentation.screens.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.navigation.NavHostController
import com.lagoscountryclub.squash.lccsquash.R
import com.lagoscountryclub.squash.lccsquash.presentation.viewmodels.AdminViewModel

@Composable
fun AdminOptionsDialog(
    showPreview: Boolean = false,
    viewModel: AdminViewModel? = null,
    navController: NavHostController? = null,
    OnDismiss: () -> Unit = {}
) {

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
            AdminOptionItem(text = "Create Player") {

            }
            AdminOptionItem(text = "Create Game") {

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AdminOptionItem(text: String, OnClick: () -> Unit) {
    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Card(
            shape = RoundedCornerShape(8.dp),
            onClick = { OnClick.invoke() },
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
                    text = text,
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
fun AdminOptionsDialogPreview() {
    AdminOptionsDialog(showPreview = true)
}
