package com.anhar.atcadaptor.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.common.Dimens.SmallPadding

@Composable
fun CustomCircularProgress( isLoading: Boolean , modifier: Modifier = Modifier) {

    if (isLoading) {
        Box(modifier = modifier.fillMaxSize()){
            Card(
                modifier = Modifier.align(Alignment.Center) ,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(SmallPadding)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = CircleShape.copy(CornerSize(15.dp))
                        )
                        .padding(10.dp), color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }
}