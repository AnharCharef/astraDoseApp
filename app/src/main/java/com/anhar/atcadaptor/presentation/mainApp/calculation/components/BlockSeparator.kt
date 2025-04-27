package com.anhar.atcadaptor.presentation.mainApp.calculation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.common.Dimens.SmallPadding

@Composable
fun BlockSeparator(
    modifier: Modifier = Modifier,
    sectionTitle: String,
    showItems: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {

    val density = LocalDensity.current
    val enterTransition = slideInVertically {
        with(density) { -40.dp.roundToPx() }
    } + expandVertically(
        expandFrom = Alignment.Top
    ) + fadeIn(
        initialAlpha = 0.3f
    )

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = SmallPadding)
                .clip(CircleShape)
                .clickable {
                    onClick()
                }
                .padding(horizontal = SmallPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sectionTitle,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier
                    .rotate(if (showItems) 0f else 180f)
                    .offset(
                        y = if (showItems) (-0).dp else (-2).dp
                    )


            )
        }
        AnimatedVisibility(
            showItems, enter = enterTransition,
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            content()
        }
    }
}