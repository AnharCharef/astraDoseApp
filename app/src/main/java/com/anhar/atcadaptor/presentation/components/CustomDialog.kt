package com.anhar.atcadaptor.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.anhar.atcadaptor.R
import java.util.Locale

@Composable
fun CustomDialog(
    title: String,
    message: String,
    showDialog: Boolean,
    onConfirmRequest: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    if (showDialog) {

        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .width(300.dp)
                    .wrapContentHeight()
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        MaterialTheme.colorScheme.background,
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium.copy(
//                        fontFamily = Cairo
                    ),
                    textAlign = TextAlign.Center
                )
                // Message
                Text(
                    message,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal
                    )
                )

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Cancel button
                    Button(
                        onClick = { onDismissRequest() },
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // Action button
                    Button(
                        onClick = {
                            onConfirmRequest()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                    ) {
                        Text(
                            text = stringResource(id = R.string.ok),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }
            }

        }
    }

}


@Composable
fun LeavingAppDialog(
    showDialog: Boolean,
    onConfirmRequest: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val currentLang = Locale.getDefault().language
    CompositionLocalProvider(
        if (currentLang != "ar")
            LocalLayoutDirection provides LayoutDirection.Ltr
        else LocalLayoutDirection provides LayoutDirection.Rtl
    )
    {
        CustomDialog(
            title = stringResource(id = R.string.leaveTheApp),
            message = stringResource(id = R.string.doYouReallyWantToLeave),
            showDialog = showDialog,
            onDismissRequest = {
                onDismissRequest()
            },
            onConfirmRequest = {
                onConfirmRequest()
            }
        )
    }
}