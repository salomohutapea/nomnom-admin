package com.example.nomnom.ui.pesanan

import android.annotation.SuppressLint
import com.example.nomnom.R
import com.example.nomnom.models.OrderModel
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.view_order_item.view.*

class PesananAdapter(val data: OrderModel) : Item<ViewHolder>() {

    private var viewModel = PesananViewModel()

    override fun getLayout(): Int {
        return R.layout.view_order_item
    }

    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.view_order_tablenumber.text = "Meja ${data.tableNo}"
        viewHolder.itemView.view_order_moreinfo.setOnClickListener {
            viewModel.openMoreInfo(data.orderId.toString())
        }

    }
}