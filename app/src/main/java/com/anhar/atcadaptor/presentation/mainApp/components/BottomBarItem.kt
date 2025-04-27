package com.anhar.atcadaptor.presentation.mainApp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.common.Dimens
import com.anhar.atcadaptor.domain.model.bottomBar.BottomBar


@Stable
@Composable
fun BottomBarItem(
    modifier : Modifier = Modifier,
    currentScreen : Int,
    item: BottomBar,
    onChangeNav: (Int) -> Unit
){
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .background(
                color = if (item.screen == currentScreen) MaterialTheme.colorScheme.background else Color.Transparent,
                shape = CircleShape
            )
            .padding(Dimens.SmallPadding)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { onChangeNav(item.screen) }) {
        Icon(
            painter = if (item.screen == currentScreen) painterResource( item.selectedIcon) else painterResource( item.unselectedIcon),
            contentDescription = item.title,
            tint = if (item.screen == currentScreen) MaterialTheme.colorScheme.primary else Color.Gray,
            modifier = Modifier.size(25.dp)
        )
    }
}