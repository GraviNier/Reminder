package com.example.reminder

import TimePick
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val CHANNEL_ID = "channel_id"
private const val CHANNEL_NAME = "channel_name"
private const val CHANNEL_DESCRIPTION = "channel_description "

class MainActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener   {
    private var year = 0
    private var monthOfYear = 0
    private var dayOfMonth = 0
    private var hourOfDay = 0
    private var minute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createChannel()
        buttonNotification.setOnClickListener {
            scheduleNotification(editTextNotificationContent.text.toString())
        }
        textDate.setOnClickListener{
            val newFragment = DatePick()
            newFragment.show(supportFragmentManager, "datePicker")
        }

        textTime.setOnClickListener{
            val newFragmentTime = TimePick()
            newFragmentTime.show(supportFragmentManager, "timePicker")
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        this.year = year
        this.monthOfYear = monthOfYear
        this.dayOfMonth = dayOfMonth
        val str = String.format(Locale.JAPAN, "%d/%d/%d", year, monthOfYear+1, dayOfMonth)
        textDate.text = str
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        this.hourOfDay = hourOfDay
        this.minute = minute
        val str = " " + String.format(Locale.JAPAN, "%d:%d", hourOfDay, minute)
        textTime.text = str
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
        var calendar  = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(year,monthOfYear,dayOfMonth,hourOfDay, minute,0)

        val a = calendar.time


        val notificationIntent = Intent(this, AlarmReceiver::class.java)
        notificationIntent.putExtra(NOTIFICATION_CONTENT,content)

        val pendingIntent = PendingIntent.getBroadcast(this,0,notificationIntent,0)

        val alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(this,"SetAlarm", Toast.LENGTH_SHORT).show()
    }
}
