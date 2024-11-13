package ro.pub.cs.systems.eim.practicaltest01var07

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import kotlin.random.Random

class PracticalTest01Var07Service : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private val broadcastIntent = Intent("ro.pub.cs.systems.eim.practicaltest01var07.UPDATE_FIELDS")

    private val runnable = object : Runnable {
        override fun run() {
            // Generate four random integers
            val randomValues = List(4) { Random.nextInt(1, 100) }
            broadcastIntent.putExtra("value1", randomValues[0])
            broadcastIntent.putExtra("value2", randomValues[1])
            broadcastIntent.putExtra("value3", randomValues[2])
            broadcastIntent.putExtra("value4", randomValues[3])

            // Send broadcast
            sendBroadcast(broadcastIntent)

            // Schedule the next execution after 10 seconds
            handler.postDelayed(this, 10000)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start broadcasting every 10 seconds
        handler.post(runnable)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable) // Stop broadcasting when service is destroyed
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
