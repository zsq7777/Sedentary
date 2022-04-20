package com.able.timeing

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX

class MyService : Service() {


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startForeground()


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val vibrator = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        Thread(Runnable {
            while (true){
                vibrator.vibrate(10000)
                Thread.sleep(1000*60*16)
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
//        return START_STICKY
    }

    /**
     * 启动前台服务
     */
    private fun startForeground() {
        var channelId: String? = null
        // 8.0 以上需要特殊处理
        channelId = createNotificationChannel("checkin", "ForegroundService")
        channelId?.let {
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            val notification: Notification = builder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(PRIORITY_MAX)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()
            startForeground(1, notification)
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String? {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}