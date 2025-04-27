package com.anhar.atcadaptor.presentation.mainApp.profile.components

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.common.Dimens.ExtraSmallPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding

@Composable
fun ImageSection(
    modifier: Modifier = Modifier,
    userQrImageBase64: String?,
    userName: String ,
    reGenrateQrCode : ()-> Unit
) {
    val bitmap = remember(userQrImageBase64) {
        userQrImageBase64?.let {
            try {
                val imageBytes = Base64.decode(it, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            } catch (e: Exception) {
                null
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = SmallPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding)
    ) {
        Box(modifier = Modifier.size(if(bitmap == null )100.dp else 180.dp)) {
            bitmap?.let {
                Image(
                    painter = BitmapPainter(it.asImageBitmap()),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(SmallPadding))
                )
            } ?: TextButton(
                onClick = {
                    reGenrateQrCode()
                }
            ) {
                Text("QR image not available" , style = MaterialTheme.typography.bodyMedium.copy(
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold ,
                    textAlign = TextAlign.Center
                ))
            }
        }

        Text(
            text = userName,
            style = MaterialTheme.typography.titleSmall
        )
    }
}
