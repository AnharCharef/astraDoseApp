package com.anhar.atcadaptor.presentation.mainApp.calculation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Dimens
import com.anhar.atcadaptor.common.Dimens.ExtraSmallPadding
import com.anhar.atcadaptor.common.Dimens.ExtraSmallPadding2
import com.anhar.atcadaptor.common.Dimens.LargePadding
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding


@Composable
fun DropdownMenuLabel(
    modifier: Modifier = Modifier,
    name: String,
    menu: List<Any> = emptyList(),
    isError: Boolean,
    expanded: Boolean,
    selectedItem: Any = Any(),
    onSelectItemClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
    errorMessage: String = "",
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(vertical = Dimens.SmallPadding)
            .padding(bottom = SmallPadding)
    ) {
        Column(modifier = modifier) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .sizeIn(minHeight = 40.dp)
                    .clip(RoundedCornerShape(40))
                    .border(
                        width = 1.dp,
                        color = if (isError) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(40)
                    )
                    .clickable {
                        onClick()
                    }
                    .padding(vertical = SmallPadding.plus(ExtraSmallPadding)),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = name, style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = MediumPadding.minus(4.dp))
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .size(LargePadding)
                        .padding(ExtraSmallPadding)
                        .rotate(
                            180f
                        )
                )
            }
            if (isError) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = MediumPadding, top = ExtraSmallPadding2),
                    style = MaterialTheme.typography.bodySmall,
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        Box(modifier = Modifier.align(Alignment.End)) {
            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = SmallPadding)
                    .sizeIn(maxHeight = 300.dp)
                    .animateContentSize(),
                expanded = expanded, onDismissRequest = {
                    onDismissRequest()
                }) {

                menu.forEach { menuItem ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (selectedItem == menuItem)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                else MaterialTheme.colorScheme.background,
                                RoundedCornerShape(SmallPadding)
                            )
                            .clip(RoundedCornerShape(SmallPadding)),
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = menuItem.toString(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }, onClick = {
                            onSelectItemClick(menuItem.toString())
                        })
                }
            }
        }
    }
}