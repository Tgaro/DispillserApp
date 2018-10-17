package com.dispillser.tiago.dispillserapp.Firebase
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.content.Context.NOTIFICATION_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.app.NotificationManager
import com.dispillser.tiago.dispillserapp.R.mipmap.ic_launcher
import com.dispillser.tiago.dispillserapp.R.color.colorPrimaryLight
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Context
import android.support.v4.app.NotificationCompat
import com.dispillser.tiago.dispillserapp.R
import android.app.NotificationChannel
import android.app.TaskStackBuilder
import com.dispillser.tiago.dispillserapp.MainActivity
import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat.getSystemService

class FirebaseMessagingService : FirebaseMessagingService() {
    var NOTIFICATION_ID = 234
    private var count = 0
    private var CHANNEL_ID = "CHANNEL_DISP"
    private var name: CharSequence = "DISPILLSER"
    private var Description = "Dispillser Channel"


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "Data Message Received ")
        if (remoteMessage.notification != null) {
            Log.d(TAG, "From: " + remoteMessage.from)
            Log.d(TAG, "Notification Message Body: " + remoteMessage.notification!!.body)
            //Calling method to generate notification
            sendNotification(remoteMessage.notification!!.title,
                    remoteMessage.notification!!.body, remoteMessage.data)
        }else if (remoteMessage.data.isNotEmpty()){
            val data = remoteMessage.data.toMap()
            Log.d(TAG, "Data Message Body: " +  data["body"])
            sendNotification(data["title"], data["body"], remoteMessage.data)
        }
    }

    companion object {
        private const val TAG = "FCM Service"
    }

    //This method is only generating push notification
    private fun sendNotification(messageTitle: String?, messageBody: String?, row: Map<String, String>) {

        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = Description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.GREEN
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mChannel.setShowBadge(false)
            notificationManager.createNotificationChannel(mChannel)
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)

        val resultIntent = Intent(this, MainActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentIntent(resultPendingIntent)

        notificationManager.notify(NOTIFICATION_ID, builder.build())

    }
}