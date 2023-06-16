package com.lagoscountryclub.squash.lccsquash.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lagoscountryclub.squash.lccsquash.R
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayerItem(
    player: Player,
    isLeaderboard: Boolean = false,
    position: Int = 0,
    OnClick: (player: Player) -> Unit = {}
) {
    Box(modifier = Modifier.padding(8.dp)) {
        Card(
            onClick = { OnClick.invoke(player) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isLeaderboard) {
                    val color = when (position) {
                        0 -> Gold
                        1 -> Silver
                        2 -> Bronze
                        else -> MaterialTheme.colors.secondary
                    }

                    Box {
                        Image(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "star",
                            modifier = Modifier
                                .size(42.dp),
                            colorFilter = ColorFilter.tint(color)
                        )
                        Text(
                            text = "${position + 1}",
                            modifier = Modifier.align(
                                Alignment.Center
                            ),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                } else {
                    ProfileImage()
                }
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
                if (isLeaderboard) {
                    val suffix = "(Point${if (player.points == 1) "" else "s"})"
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp),
                        text = "${player.points} $suffix",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileImage(modifier: Modifier = Modifier, size: Dp = 40.dp, borderColour: Color = PrimaryColour) {
    Image(
        imageVector = Icons.Rounded.Person,
        contentDescription = stringResource(R.string.person_icon),
        colorFilter = ColorFilter.tint(color = PersonIconTint),
        modifier = modifier
            .padding(4.dp)
            .border(
                border = BorderStroke(2.dp, borderColour),
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