package com.example.reminder

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.reminder.alerm.AlarmReceiver
import com.example.reminder.alerm.NOTIFICATION_CONTENT
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val CHANNEL_ID = "channel_id"
private const val CHANNEL_NAME = "channel_name"
private const val CHANNEL_DESCRIPTION = "channel_description "

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createChannel()
        buttonNotification.setOnClickListener {
            scheduleNotification(editTextNotificationContent.text.toString())
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

    private fun scheduleNotification(content :String){
        var calendar  = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND,5)

        val notificationIntent = Intent(this,AlarmReceiver::class.java)
        notificationIntent.putExtra(NOTIFICATION_CONTENT,content)

        val pendingIntent = PendingIntent.getBroadcast(this,0,notificationIntent,0)

        val alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(this,"SetAlarm", Toast.LENGTH_SHORT).show()
    }
}
