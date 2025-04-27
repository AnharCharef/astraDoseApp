package com.anhar.atcadaptor.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Dimens
import com.anhar.atcadaptor.common.Dimens.SocialMediaItemSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(title: String, isArabic: Boolean ,onBackClicked: () -> Unit) {

    Card(shape = RoundedCornerShape(0),
        elevation  = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            title = {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )
            },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = Dimens.SmallPadding)
                .padding(top = Dimens.SmallPadding),
            navigationIcon = {
                CustomIcon(
                    modifier = Modifier.rotate(
                        if (isArabic) 180f else 0f
                    ),
                    imageVector = Icons.Default.ArrowBack
                ) {
                    onBackClicked()
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    isArabic: Boolean,
    onSaveClick: () -> Unit,
    onBackClicked: () -> Unit
) {

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimens.SmallPadding)
            .padding(top = Dimens.SmallPadding),
        navigationIcon = {
            CustomIcon(
                modifier = Modifier.rotate(
                    if (isArabic) 180f else 0f
                ),
                imageVector = Icons.Default.ArrowBack
            ) {
                onBackClicked()
            }
        },
        actions = {
            Box(modifier = Modifier
                .size(SocialMediaItemSize)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .clickable {
                    onSaveClick()
                }) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.save),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                )
            }

        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(title: String,actions : @Composable RowScope.() -> Unit ,isArabic: Boolean ,onBackClicked: () -> Unit) {

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimens.SmallPadding)
            .padding(top = Dimens.SmallPadding),
        navigationIcon = {
            CustomIcon(
                modifier = Modifier.rotate(
                    if (isArabic) 180f else 0f
                ),
                imageVector = Icons.Default.ArrowBack
            ) {
                onBackClicked()
            }
        },
        actions = actions
    )
}