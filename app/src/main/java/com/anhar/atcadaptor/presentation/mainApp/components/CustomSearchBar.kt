package com.anhar.atcadaptor.presentation.mainApp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Dimens.ExtraSmallPadding2
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SearchBarHeight
import com.anhar.atcadaptor.common.Dimens.SmallPadding

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    isEnabled: (Boolean) = true,
    height: Dp = SearchBarHeight,
    elevation: Dp = ExtraSmallPadding2,
    cornerShape: Shape = CircleShape,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    onTextCleared: () -> Unit = {},
    onBoxCLicked:() -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .height(height)
            .shadow(elevation = elevation, shape = cornerShape)
            .background(color = backgroundColor, shape = cornerShape)
            .clickable { onBoxCLicked() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            modifier = Modifier
                .weight(5f)
                .padding(start = MediumPadding, end = SmallPadding)
                .focusable(isEnabled)
                .clickable(interactionSource, indication = null) {
                    if (isEnabled) onSearchClicked() else
                        onBoxCLicked()
                }
            ,
            value = value,
            onValueChange = {
                if (isEnabled)
                onTextChange(it)
            },
            enabled = isEnabled,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Normal
            ),

            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked() }),
            singleLine = true,
            cursorBrush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.primary
                )
            )
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentSize()
                .background(color = Color.Transparent, shape = CircleShape)
                .clickable() {
                    if (value.isNotEmpty()) {
                        onTextCleared()
                    }
                    if (!isEnabled) {
                        onBoxCLicked()
                    }
                },
        ) {
            if (value.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(SmallPadding),
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.search),
                    tint = MaterialTheme.colorScheme.primary,
                )
            } else {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(SmallPadding),
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}