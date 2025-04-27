package com.anhar.atcadaptor.presentation.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermissionHandler() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val notificationPermission =
            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

        LaunchedEffect(notificationPermission) {
            if (!notificationPermission.status.isGranted) {
                notificationPermission.launchPermissionRequest()
            }
        }
    }

}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermissionHandler(
    isNotificationEnable: Boolean,
    onPermissionDenied: () -> Unit,
    onPermissionGranted: () -> Unit
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val context = LocalContext.current
        val notificationPermissionState = rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS
        )

        // Only check if notifications are enabled
        if (isNotificationEnable) {
            if (!isNotificationPermissionGranted(context)) {
                // Request the notification permission
                LaunchedEffect(notificationPermissionState) {
                    notificationPermissionState.launchPermissionRequest()
                }

                when {
                    notificationPermissionState.status.isGranted -> {
                        // Permission is granted
                        onPermissionGranted()
                    }
                    notificationPermissionState.status.shouldShowRationale -> {
                        // Permission denied but not permanently (dialog can be shown again)
                        LaunchedEffect (Unit){
                            notificationPermissionState.launchPermissionRequest()
                        }

                    }
                    else -> {
                        // Permission permanently denied (user checked "Don't ask again")
                        if (!notificationPermissionState.status.isGranted) {
                            Toast.makeText(
                                context,
                                "",
//                                context.getString(R.string.notificationPermissionDeniedMessage),
                                Toast.LENGTH_LONG
                            ).show()
                            onPermissionDenied()
                        }
                    }
                }

            } else {
                // Permission already granted, proceed
                onPermissionGranted()
            }
        }
    }
}



fun isNotificationPermissionGranted(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        // For older versions, return true since notification permission isn't required
        true
    }
}