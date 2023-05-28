package com.lagoscountryclub.squash.lccsquash.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lagoscountryclub.squash.lccsquash.R
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.ui.theme.Purple200

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayerItem(player: Player, OnClick: (player: Player) -> Unit = {}) {
    Box(modifier = Modifier.padding(8.dp)) {
        Card(
            onClick = { OnClick.invoke(player) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileImage()
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ProfileImage(size: Dp = 40.dp) {
    Image(
        imageVector = Icons.Rounded.Person,
        contentDescription = stringResource(R.string.person_icon),
        modifier = Modifier
            .padding(4.dp)
            .border(
                border = BorderStroke(2.dp, Purple200),
                shape = CircleShape
            )
            .size(size)
    )
}

@Preview
@Composable
fun PlayerItemPreview() {
    PlayerItem(Player(id = 0, name = "John Doe"))
}