package com.example.weatherapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class TimerService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    private val timer = Timer()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val time = intent?.getDoubleExtra(ELAPSED_TIME, 0.0)
        timer.scheduleAtFixedRate(time?.let { TimeTask(it) }, 1800000, 1800000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    private inner class TimeTask(private var time: Double) : TimerTask() {
        override fun run() {
            val intent = Intent(TIMER_UPDATED)
            time += 30
            intent.putExtra(ELAPSED_TIME, time)
            sendBroadcast(intent)
        }

    }

    companion object {
        const val TIMER_UPDATED = "timerUpdated"
        const val ELAPSED_TIME = "elapsedTime"
    }
}