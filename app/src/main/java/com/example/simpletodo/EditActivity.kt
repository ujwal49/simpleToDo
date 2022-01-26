package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.app.NotificationCompat.getExtras

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)


        title = "Edit Task"


        val etItem = findViewById<EditText>(R.id.etItem)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val itemData = getIntent().getStringExtra("item_text")

        etItem.setText(itemData)

        btnSave.setOnClickListener {
            val data = Intent()

            data.putExtra("item_text", etItem.getText().toString())
            data.putExtra("item_position", getIntent().getStringExtra("item_position")?.toInt())

            setResult(20,data)
            finish()
        }
    }
}