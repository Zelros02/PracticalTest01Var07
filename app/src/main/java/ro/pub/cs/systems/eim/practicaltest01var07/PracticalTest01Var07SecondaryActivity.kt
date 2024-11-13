package ro.pub.cs.systems.eim.practicaltest01var07

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01Var07SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practica_test01_var07_secondary)

        // Retrieve values from the intent
        val value1 = intent.getIntExtra("value1", 0)
        val value2 = intent.getIntExtra("value2", 0)
        val value3 = intent.getIntExtra("value3", 0)
        val value4 = intent.getIntExtra("value4", 0)

        // Set values in TextViews
        findViewById<TextView>(R.id.field1).text = "$value1"
        findViewById<TextView>(R.id.field2).text = "$value2"
        findViewById<TextView>(R.id.field3).text = "$value3"
        findViewById<TextView>(R.id.field4).text = "$value4"

        val sumButton: Button = findViewById(R.id.sumButton)
        val productButton: Button = findViewById(R.id.productButton)

        sumButton.setOnClickListener {
            val sum = value1 + value2 + value3 + value4
            val resultIntent = Intent()
            resultIntent.putExtra("resultSum", sum)
            setResult(1, resultIntent)
            finish()
        }

        productButton.setOnClickListener {
            val product = value1 * value2 * value3 * value4
            val resultIntent = Intent()
            resultIntent.putExtra("resultProduct", product)
            setResult(2, resultIntent)
            finish()
        }
    }
}