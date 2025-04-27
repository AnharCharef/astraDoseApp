package com.anhar.atcadaptor.presentation.mainApp.alert.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.domain.model.notification.Notification
import com.anhar.atcadaptor.presentation.components.CustomTopAppBar
import com.anhar.atcadaptor.presentation.mainApp.home.HomeScreen

class NotificationDetailsScreen(private val notification: Notification) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
           topBar =  {
                CustomTopAppBar(title = "Notification Details", isArabic = false) {
                    if (navigator.canPop) {
                        navigator.pop()
                    } else {
                        navigator.replaceAll(HomeScreen())
                    }
                }

            }
        ) {innerPadding ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(vertical = SmallPadding)
                    .clip(RoundedCornerShape(SmallPadding)),
                shape = RoundedCornerShape(SmallPadding),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = notification.body,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = notification.time,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
        }
    }
}