package com.draco.projector

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var address: EditText
    private lateinit var port: EditText
    private lateinit var password: EditText
    private lateinit var https: MaterialCheckBox
    private lateinit var start: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        address = findViewById(R.id.address)
        port = findViewById(R.id.port)
        password = findViewById(R.id.password)
        https = findViewById(R.id.https)
        start = findViewById(R.id.start)

        getPreferences(Context.MODE_PRIVATE).apply {
            address.setText(getString("address", "192.168.0.1"))
            port.setText(getString("port", "9999"))
            password.setText(getString("password", ""))
            https.isChecked = getBoolean("https", false)
        }

        start.setOnClickListener {
            val intent = Intent(this, ProjectorView::class.java).apply {
                putExtra("address", address.text.toString())
                putExtra("port", port.text.toString())
                putExtra("password", password.text.toString())
                putExtra("https", https.isChecked)
            }

            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        getPreferences(Context.MODE_PRIVATE).apply {
            with (edit()) {
                putString("address", address.text.toString())
                putString("port", port.text.toString())
                putString("password", password.text.toString())
                putBoolean("https", https.isChecked)
                apply()
            }
        }
    }
}