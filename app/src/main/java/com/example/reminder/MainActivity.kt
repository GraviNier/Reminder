package com.example.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

private const val CHANNEL_ID = "channel_id"
private const val CHANNEL_NAME = "channel_name"
private const val CHANNEL_DESCRIPTION = "channel_description "

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createChannel()

        var notificationId = 0   /// notificationID

        buttonNotification.setOnClickListener {
            /// 通知の中身
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)    /// 表示されるアイコン
                .setContentTitle("Notification")                  /// 通知タイトル
                .setContentText(editTextNotificationContent.text.toString())           /// 通知コンテンツ
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)   /// 通知の優先度

            /// ボタンを押して通知を表示
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, builder.build())
                notificationId += 1
            }
        }
    }

    private fun createChannel(){
        ///APIレベルに応じてチャネルを作成
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
            }
            /// チャネルを登録
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
