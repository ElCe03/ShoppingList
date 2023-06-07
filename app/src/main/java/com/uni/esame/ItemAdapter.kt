package com.uni.esame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class ItemAdapter(context: Context, private val itemList: MutableList<Item>) : ArrayAdapter<Item>(context, 0, itemList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_list_row, parent, false)
        }

        val currentItem = itemList[position]

        val itemNameTextView = itemView?.findViewById<TextView>(R.id.itemNameTextView)
        val itemQuantityTextView = itemView?.findViewById<TextView>(R.id.itemQuantityTextView)
        val deleteButton = itemView?.findViewById<Button>(R.id.deleteButton)

        itemNameTextView?.text = currentItem.name
        itemQuantityTextView?.text = currentItem.quantity.toString()

        deleteButton?.setOnClickListener {
            itemList.removeAt(position)
            notifyDataSetChanged()
        }

        return itemView!!
    }
}


