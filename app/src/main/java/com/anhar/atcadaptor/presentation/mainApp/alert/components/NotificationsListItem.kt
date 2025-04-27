package com.anhar.atcadaptor.presentation.mainApp.alert.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.common.Constant.getRelativeTime
import com.anhar.atcadaptor.common.Dimens.ExtraSmallPadding2
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.domain.model.notification.Notification

@Composable
fun NotificationsListItem(modifier: Modifier = Modifier, notification: Notification , onClick : (Notification) -> Unit) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (notification.unread) MaterialTheme.colorScheme.primary.copy(0.1f)
                else Color.Transparent,
                shape = RoundedCornerShape(SmallPadding)
            )
            .clip(RoundedCornerShape(SmallPadding))
            .clickable {
                onClick(notification)
            }
            .padding(horizontal = SmallPadding, vertical = ExtraSmallPadding2),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            Modifier
                .weight(1.5f)
                .offset(y = (-4).dp)
        ) {
            Text(
                text = notification.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis ,
                textAlign = TextAlign.Start
            )
            Text(
                text = notification.body, style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
        }
        Text(
            text = getRelativeTime(notification.time), style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.weight(0.5f) ,
            textAlign = TextAlign.End
        )
    }

}