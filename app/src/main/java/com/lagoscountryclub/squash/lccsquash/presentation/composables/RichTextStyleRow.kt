package com.lagoscountryclub.squash.lccsquash.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.model.RichTextStyle
import com.mohamedrejeb.richeditor.model.RichTextValue

@Composable
fun RichTextStyleRow(
    modifier: Modifier = Modifier,
    value: RichTextValue,
    onValueChanged: (RichTextValue) -> Unit,
) {
    LazyRow(
        modifier = modifier
    ) {
        item {
            RichTextStyleButton(
                style = RichTextStyle.Bold,
                value = value,
                onValueChanged = onValueChanged,
                icon = Icons.Outlined.FormatBold
            )
        }

        item {
            RichTextStyleButton(
                style = RichTextStyle.Italic,
                value = value,
                onValueChanged = onValueChanged,
                icon = Icons.Outlined.FormatItalic
            )
        }

        item {
            RichTextStyleButton(
                style = RichTextStyle.Underline,
                value = value,
                onValueChanged = onValueChanged,
                icon = Icons.Outlined.FormatUnderlined
            )
        }

        item {
            RichTextStyleButton(
                style = RichTextStyle.Strikethrough,
                value = value,
                onValueChanged = onValueChanged,
                icon = Icons.Outlined.FormatStrikethrough
            )
        }

        item {
            RichTextStyleButton(
                style = RichTextStyle.FontSize(28.sp),
                value = value,
                onValueChanged = onValueChanged,
                icon = Icons.Outlined.FormatSize
            )
        }

        item {
            RichTextStyleButton(
                style = RichTextStyle.TextColor(Color.Red),
                value = value,
                onValueChanged = onValueChanged,
                icon = Icons.Filled.Circle,
                tint = Color.Red
            )
        }

        item {
            RichTextStyleButton(
                style = CustomStyle(color = Color.Blue, background = Color.Green),
                value = value,
                onValueChanged = onValueChanged,
                icon = Icons.Outlined.Circle,
                tint = Color.Green
            )
        }
    }
}


@Composable
fun RichTextStyleButton(
    style: RichTextStyle,
    value: RichTextValue,
    onValueChanged: (RichTextValue) -> Unit,
    icon: ImageVector,
    tint: Color? = null
) {
    IconButton(
        modifier = Modifier
            .focusProperties { canFocus = false },
        onClick = {
            onValueChanged(value.toggleStyle(style))
        },
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = if (value.currentStyles.contains(style)) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onBackground
            },
        ),
    ) {
        Icon(
            icon,
            contentDescription = icon.name,
            tint = tint ?: LocalContentColor.current,
            modifier = Modifier
                .background(
                    color = if (value.currentStyles.contains(style)) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Transparent
                    },
                    shape = CircleShape
                )
        )
    }
}

data class CustomStyle(
    val color: Color,
    val background: Color
) : RichTextStyle {
    override fun applyStyle(spanStyle: SpanStyle): SpanStyle {
        return spanStyle.copy(
            color = color,
            background = background
        )
    }
}