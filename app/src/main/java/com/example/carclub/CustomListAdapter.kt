package com.example.carclub

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomListAdapter(
    private val context: Context,
    private val itemList: List<ItemModel>,
) : BaseAdapter() {

    override fun getCount(): Int = itemList.size

    override fun getItem(position: Int): ItemModel = itemList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        val item = getItem(position)

        val ivIcon = view.findViewById<ImageView>(R.id.ivItemIcon)
        val tvName = view.findViewById<TextView>(R.id.tvItemName)

        ivIcon.setImageResource(item.imageResId)
        tvName.text = item.name

        return view
    }
}
