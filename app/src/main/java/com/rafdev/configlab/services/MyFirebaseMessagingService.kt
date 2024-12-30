package com.rafdev.configlab.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rafdev.configlab.main.MainActivity
import com.rafdev.configlab.R
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        const val CHANNEL_NAME = "FCM notification channel"
    }
    private val random = Random

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            sendNotification(it)
        }
    }

    private fun sendNotification(it: RemoteMessage.Notification) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelId = this.getString(R.string.default_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(it.title)
            .setContentText(it.body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_fcm)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        manager.notify(random.nextInt(), notificationBuilder.build())

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

}