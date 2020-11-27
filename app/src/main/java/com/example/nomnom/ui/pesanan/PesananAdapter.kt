package com.example.nomnom.ui.pesanan

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.example.nomnom.R
import com.example.nomnom.activities.OrderMoreInfoActivity
import com.example.nomnom.models.OrderModel
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.view_order_item.view.*
import java.io.Serializable

class PesananAdapter(val data: OrderModel, private val context: Context) : Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.view_order_item
    }

    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.view_order_tablenumber.text = "Meja ${data.tableNo}"
        viewHolder.itemView.vieworderedmenu_all.setOnClickListener {
            val intent = Intent(it.context, OrderMoreInfoActivity::class.java).putExtra(
                "DATA",
                data as Serializable
            )
            it.context.startActivity(intent)
        }

    }
}