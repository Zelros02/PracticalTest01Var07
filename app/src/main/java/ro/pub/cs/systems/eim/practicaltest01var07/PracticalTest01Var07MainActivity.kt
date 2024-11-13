package ro.pub.cs.systems.eim.practicaltest01var07

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var07MainActivity : AppCompatActivity() {

    private val updateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // Retrieve random values from the broadcast
            val value1 = intent?.getIntExtra("value1", 0) ?: 0
            val value2 = intent?.getIntExtra("value2", 0) ?: 0
            val value3 = intent?.getIntExtra("value3", 0) ?: 0
            val value4 = intent?.getIntExtra("value4", 0) ?: 0

            // Update the EditText fields
            findViewById<EditText>(R.id.field1).setText(value1.toString())
            findViewById<EditText>(R.id.field2).setText(value2.toString())
            findViewById<EditText>(R.id.field3).setText(value3.toString())
            findViewById<EditText>(R.id.field4).setText(value4.toString())
        }
    }


    private lateinit var activityLauncher: ActivityResultLauncher<Intent>
    private var sum: Int = 0
    private var product: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_var07_main)

        val serviceIntent = Intent(this, PracticalTest01Var07Service::class.java)
        startService(serviceIntent)

        // Register the broadcast receiver
        registerReceiver(updateReceiver, IntentFilter("ro.pub.cs.systems.eim.practicaltest01var07.UPDATE_FIELDS"),
            RECEIVER_EXPORTED
        )

        savedInstanceState?.let {
            sum = it.getInt("sum", 0)
            product = it.getInt("product", 0)
            Toast.makeText(this, "Sum: $sum, Product: $product", Toast.LENGTH_LONG).show()
        }

        // Initialize the ActivityResultLauncher
        activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                sum = result.data?.getIntExtra("resultSum", 0) ?: 0
                product = result.data?.getIntExtra("resultProduct", 0) ?: 0

                // Display sum and product in a Toast
                Toast.makeText(this, "Sum: $sum, Product: $product", Toast.LENGTH_LONG).show()
                Log.d("MainActivity", "Sum: $sum, Product: $product")
            }
        }

        val setButton: Button = findViewById(R.id.setButton)
        setButton.setOnClickListener {
            val field1 = findViewById<EditText>(R.id.field1)
            val field2 = findViewById<EditText>(R.id.field2)
            val field3 = findViewById<EditText>(R.id.field3)
            val field4 = findViewById<EditText>(R.id.field4)

            if (listOf(field1, field2, field3, field4).all { it.text.toString().toIntOrNull() != null }) {
                val intent = Intent(this, PracticaTest01Var07SecondaryActivity::class.java)
                intent.putExtra("value1", field1.text.toString().toInt())
                intent.putExtra("value2", field2.text.toString().toInt())
                intent.putExtra("value3", field3.text.toString().toInt())
                intent.putExtra("value4", field4.text.toString().toInt())
                activityLauncher.launch(intent)
            } else {
                Toast.makeText(this, "All fields must contain numbers", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("sum", sum)
        outState.putInt("product", product)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the service when the main activity is destroyed
        val serviceIntent = Intent(this, PracticalTest01Var07Service::class.java)
        stopService(serviceIntent)

        // Unregister the broadcast receiver
        unregisterReceiver(updateReceiver)
    }
}