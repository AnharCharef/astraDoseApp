package com.anhar.atcadaptor.presentation.mainApp.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Dimens.MediumPadding


@Stable
@Composable
fun EmptyScreen( error: String? = null, searchQuery : String? = null ,emptyMessage: String="" , onClick: (() -> Unit)? = null) {

    var message by remember {
        mutableStateOf(error)
    }

    var icon by remember {
        mutableIntStateOf(R.drawable.ic_network_error)
    }

    if (error == null) {
        message = emptyMessage
        icon = R.drawable.ic_search_document
    }

    if(!searchQuery.isNullOrEmpty()){
        message = emptyMessage
    }else if (searchQuery== ""){
        message = "Recherche de quelque chose"
        icon = R.drawable.ic_search_document
    }


    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 0.3f else 0f,
        animationSpec = tween(durationMillis = 1500), label = ""
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    EmptyContent(alphaAnim = alphaAnimation, message = message ?: "", iconId = icon , onClick)

}

@Stable
@Composable
fun EmptyContent(alphaAnim: Float, message: String, iconId: Int, onClick: (() -> Unit)?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(MediumPadding))
            .alpha(0.7f)
            .clickable(enabled = onClick != null) {
                onClick?.let {
                    it()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
            modifier = Modifier
                .size(120.dp)
                .alpha(alphaAnim)
        )
        Text(
            modifier = Modifier
                .padding(10.dp)
                .alpha(alphaAnim),
            textAlign = TextAlign.Center,
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
        )
    }
}
