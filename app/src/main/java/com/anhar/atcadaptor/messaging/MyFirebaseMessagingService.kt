package com.anhar.atcadaptor.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.presentation.mainApp.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.let {
            sendNotification(it.title, it.body)
        }
        if (message.data.isNotEmpty()) {
            val title = message.data["title"] ?: getString(R.string.app_name)
            val body = message.data["body"] ?: getString(R.string.app_name)
            sendNotification(title, body)
        }
    }

    private fun sendNotification(title: String?, messageBody: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "Alerts"
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.atc_logo)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSound(soundUri) // still needed for pre-Oreo
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Fix: use IMPORTANCE_HIGH + enable sound/vibration
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setSound(soundUri, null) // you can pass AudioAttributes if needed
                enableLights(true)
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 1000)
                description = "General alerts and messages"
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }


}