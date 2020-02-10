package com.example.reminder.alerm

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.reminder.CHANNEL_ID
import com.example.reminder.R


const val NOTIFICATION_CONTENT = "content";

class AlarmReceiver : BroadcastReceiver() {
    private var notificationId:Int= 0

    override fun onReceive(context: Context?, intent: Intent?) {
        val content = intent?.getStringExtra(NOTIFICATION_CONTENT)
        with(NotificationManagerCompat.from(context!!)) {
            notify(notificationId, buildNotification(context,content))
            notificationId += 1
        }
    }

    private fun buildNotification(context: Context?,content: String?) : Notification{
        /// 通知の中身
        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)    /// 表示されるアイコン
            .setContentTitle("Notification")                  /// 通知タイトル
            .setContentText(content)           /// 通知コンテンツ
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)   /// 通知の優先度
        return builder.build()
    }

}