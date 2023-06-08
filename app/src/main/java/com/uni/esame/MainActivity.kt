package com.uni.esame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {
    private val list = arrayListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }
}
