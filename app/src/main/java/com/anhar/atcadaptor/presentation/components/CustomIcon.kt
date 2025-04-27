package com.anhar.atcadaptor.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.common.Dimens.SocialMediaItemSize

@Stable
@Composable
fun CustomIcon(
    modifier: Modifier = Modifier,
    backgroundColor : Color = MaterialTheme.colorScheme.background,
    iconColor : Color = MaterialTheme.colorScheme.onBackground,
    imageVector: ImageVector,
    onCLick: () -> Unit
) {

    Card(modifier = modifier ,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = CircleShape
    ) {
        Image(imageVector = imageVector, contentDescription = null,
            Modifier
                .size(SocialMediaItemSize)
                .clip(CircleShape)
                .clickable {
                    onCLick()
                }
                .background(
                    backgroundColor,
                    shape = CircleShape
                )
                .padding(
                    SmallPadding
                ),
            colorFilter = ColorFilter.tint(color = iconColor)
        )
    }

}


@Stable
@Composable
fun CustomIcon(
    modifier: Modifier = Modifier,
    backgroundColor : Color = MaterialTheme.colorScheme.background,
    iconColor : Color = MaterialTheme.colorScheme.onBackground,
    imagePainter: Painter,
    onCLick: () -> Unit
) {

    Card(modifier = modifier ,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = CircleShape
    ) {
        Image(painter = imagePainter, contentDescription = null,
            Modifier
                .size(SocialMediaItemSize)
                .clip(CircleShape)
                .clickable {
                    onCLick()
                }
                .background(
                    backgroundColor,
                    shape = CircleShape
                )
                .padding(
                    SmallPadding
                ),
            colorFilter = ColorFilter.tint(color = iconColor)
        )
    }

}