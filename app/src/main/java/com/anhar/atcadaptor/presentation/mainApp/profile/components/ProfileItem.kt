package com.anhar.atcadaptor.presentation.mainApp.profile.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.common.Dimens.ExtraSmallPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.common.Dimens.SocialMediaItemSize

@Stable
@Composable
fun ProfileItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    title: String ,
    isArabic: Boolean ,
    onCLick: () -> Unit
) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(SmallPadding))
                .clickable { onCLick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painter, contentDescription = null,
                modifier = Modifier
                    .size(SocialMediaItemSize)
                    .padding(vertical = ExtraSmallPadding.plus(2.dp)),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = title, style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                modifier = Modifier.padding(SmallPadding)
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.ArrowForwardIos, contentDescription = null,
                modifier = Modifier
                    .size(SocialMediaItemSize)
                    .padding(vertical = SmallPadding)
                    .rotate(if (isArabic) 180f else 0f),
                tint = MaterialTheme.colorScheme.primary
            )
    }


}

@Stable
@Composable
fun ProfileItem(
    modifier: Modifier = Modifier,
    title: String ,
    imageVector: ImageVector,
    isArabic: Boolean ,
    onCLick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(SmallPadding))
            .clickable { onCLick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector, contentDescription = null,
            modifier = Modifier
                .size(SocialMediaItemSize)
                .padding(vertical = ExtraSmallPadding.plus(2.dp)),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = title, style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            modifier = Modifier.padding(SmallPadding)
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.ArrowForwardIos, contentDescription = null,
            modifier = Modifier
                .size(SocialMediaItemSize)
                .padding(vertical = SmallPadding)
                .rotate(if (isArabic) 180f else 0f),
            tint = MaterialTheme.colorScheme.primary
        )
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Stable
@Composable
fun ProfileItem(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    title: String ,
    checked: Boolean ,
    onLongClick : () -> Unit ,
    onCheckChanged: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(SmallPadding))
            .combinedClickable(
                onClick = { onCheckChanged() },
                onLongClick = {
                 onLongClick()
                }),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector =imageVector, contentDescription = null,
            modifier = Modifier
                .size(SocialMediaItemSize)
                .padding(vertical = ExtraSmallPadding.plus(2.dp)),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = title, style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            modifier = Modifier.padding(SmallPadding)
        )

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = checked,
            onCheckedChange = { onCheckChanged() },
            modifier = Modifier
                .scale(0.7f),
            colors = SwitchDefaults.colors(
                uncheckedTrackColor = MaterialTheme.colorScheme.background,
                uncheckedBorderColor = MaterialTheme.colorScheme.primary,
            )
        )
    }


}