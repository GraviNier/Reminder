package com.example.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "channel_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createChannel()

        Log.e("",editTextNotificationContent.text.toString())

        /// 通知の中身
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)    /// 表示されるアイコン
            .setContentTitle("Notification")                  /// 通知タイトル
            .setContentText(editTextNotificationContent.text.toString())           /// 通知コンテンツ
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)   /// 通知の優先度

        var notificationId = 0   /// notificationID

        buttonNotification.setOnClickListener {
            /// ボタンを押して通知を表示
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, builder.build())
                notificationId += 1
            }
        }
    }

    fun createChannel(){

        val channel_name = "channel_name"
        val channel_description = "channel_description "
        ///APIレベルに応じてチャネルを作成
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channel_name
            val descriptionText = channel_description
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            /// チャネルを登録
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
