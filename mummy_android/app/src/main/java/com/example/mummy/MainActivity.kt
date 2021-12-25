package com.example.mummy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView1 = findViewById(R.id.textView1) as TextView
        val button1 = findViewById(R.id.button1) as Button
        val editText1 = findViewById(R.id.editText1) as EditText

        textView1.text = "Test connessione in corso..."

        //

        val address_test = "???/test"
        lifecycleScope.launch {
            val res = MyHTTP.get(address_test)
            if(res == "0kill") { textView1.text = "Connessione OK" }
        }

        //

        val url = "???/save"
        button1.setOnClickListener {
            textView1.text = "Invio in corso..."
            val body_content = editText1.text.toString()
            lifecycleScope.launch {
                val res = MyHTTP.post(url, body_content)
                if(res == "Saved.") { textView1.text = "Inviato!" }
            }
        }
    }
}
