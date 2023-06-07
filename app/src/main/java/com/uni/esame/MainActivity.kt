package com.uni.esame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
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
        val itemQuantityEditText = findViewById<EditText>(R.id.itemQuantityEditText)

        addButton.setOnClickListener {
            val itemName = itemNameEditText.text.toString()
            val itemQuantity = itemQuantityEditText.text.toString().toIntOrNull()

            if (!itemName.isNullOrEmpty() && itemQuantity != null) {
                val newItem = Item(itemName, itemQuantity)
                list.add(newItem)
                adapter.notifyDataSetChanged()

                itemNameEditText.text.clear()
                itemQuantityEditText.text.clear()

                if (list.isEmpty()) {
                    showWelcomePopup()
                }
            } else {
                Toast.makeText(this, "Please enter valid item details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showWelcomePopup() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Welcome to Your Shopping List")
        builder.setMessage("Start adding items to your list!")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}