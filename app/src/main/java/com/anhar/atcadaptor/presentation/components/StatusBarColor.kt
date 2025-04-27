package com.anhar.atcadaptor.presentation.components

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun StatusBarColor(color: Int =  MaterialTheme.colorScheme.background.toArgb()) {
    val view = LocalView.current
    val darkTheme = isSystemInDarkTheme()
//    val color = if(darkTheme) Color.Black.toArgb() else Color.White.toArgb()
//    val color = MaterialTheme.colorScheme.background.toArgb()

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = color
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }
}