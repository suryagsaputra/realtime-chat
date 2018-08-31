package project.sabil.realtime_chat

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FCMService : FirebaseMessagingService() {

    override fun onNewToken(newToken: String) {
        Log.d(TAG_ON_NEW_TOKEN, newToken)
    }

    override fun onMessageReceived(receivedMessage: RemoteMessage) {
        Log.d(TAG_ON_MESSAGE_RECEIVED, receivedMessage.data.toString())
        val title = receivedMessage.data["title"].toString()
        val message = receivedMessage.data["message"].toString()
        val notificationTitle = receivedMessage.notification?.title.toString()
        val notificationBody = receivedMessage.notification?.body.toString()
        sendNotification(notificationTitle, notificationBody, title, message)
    }

    private fun sendNotification(notificationTitle: String, notificationBody: String, dataTitle: String, dataMessage: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("title", dataTitle)
        intent.putExtra("message", dataMessage)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notificationTitle)
            .setContentText(notificationBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent) as NotificationCompat.Builder

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    companion object {
        const val TAG_ON_NEW_TOKEN = "fcm.on.new.token"
        const val TAG_ON_MESSAGE_RECEIVED = "fcm.on.message.received"
    }
}