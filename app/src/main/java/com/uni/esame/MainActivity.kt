package com.uni.esame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.NumberPicker
import android.widget.Toast
import android.content.SharedPreferences


class MainActivity : AppCompatActivity() {
    private val list = arrayListOf<Item>()
    private val REQUEST_CODE_SPEECH_INPUT = 1
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyShoppingList", MODE_PRIVATE)

        val listView = findViewById<ListView>(R.id.List)
        val adapter = ItemAdapter(this, list)
        listView.adapter = adapter

        val deleteButton = findViewById<Button>(R.id.Clear)
        deleteButton.setOnClickListener {
            list.clear()
            adapter.notifyDataSetChanged()
        }

        val addButton = findViewById<Button>(R.id.addButton)
        val itemNameEditText = findViewById<EditText>(R.id.itemNameEditText)
        val itemQuantityPicker = findViewById<NumberPicker>(R.id.itemQuantityPicker)

        itemQuantityPicker.minValue = 0
        itemQuantityPicker.maxValue = 100

        addButton.setOnClickListener {
            val itemName = itemNameEditText.text.toString()
            val itemQuantity = itemQuantityPicker.value

            if (!itemName.isNullOrEmpty()) {
                val newItem = Item(itemName, itemQuantity)
                list.add(newItem)
                adapter.notifyDataSetChanged()

                itemNameEditText.text.clear()

                if (list.isEmpty()) {
                    Toast.makeText(this, "WELCOME TO YOUR SHOPPING LIST", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter valid item details", Toast.LENGTH_SHORT).show()
            }
        }

        val voiceInputButton = findViewById<Button>(R.id.voiceInputButton)
        voiceInputButton.setOnClickListener {
            startSpeechToText()
        }

        // Ripristina la sessione salvata
        restoreSession()
    }

    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak item name")
        startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!result.isNullOrEmpty()) {
                val spokenText = result[0]
                val itemNameEditText = findViewById<EditText>(R.id.itemNameEditText)
                itemNameEditText.setText(spokenText)
            }
        }
    }

    override fun onPause() {
        super.onPause()

        // Salva la sessione corrente
        saveSession()
    }

    private fun saveSession() {
        val editor = sharedPreferences.edit()
        editor.putInt("listSize", list.size)

        for (i in 0 until list.size) {
            val item = list[i]
            editor.putString("itemName$i", item.name)
            editor.putInt("itemQuantity$i", item.quantity)
        }

        editor.apply()
    }

    private fun restoreSession() {
        val size = sharedPreferences.getInt("listSize", 0)

        for (i in 0 until size) {
            val itemName = sharedPreferences.getString("itemName$i", null)
            val itemQuantity = sharedPreferences.getInt("itemQuantity$i", 0)

            if (!itemName.isNullOrEmpty()) {
                val item = Item(itemName, itemQuantity)
                list.add(item)
            }
        }
    }
}
