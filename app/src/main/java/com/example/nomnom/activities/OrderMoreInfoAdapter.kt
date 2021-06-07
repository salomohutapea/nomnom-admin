package com.example.nomnom.activities

import android.annotation.SuppressLint
import com.example.nomnom.R
import com.example.nomnom.models.MenuModel
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.view_ordered_menu.view.*

class OrderMoreInfoAdapter(val eachMenu: MenuModel, val pesanan: ArrayList<String>) : Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.view_ordered_menu
    }

    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        Picasso.get().load(eachMenu.imgUrl).resize(50, 50)
            .centerCrop().into(viewHolder.itemView.vieworderedmenu_imageView)
        viewHolder.itemView.vieworderedmenu_harga_textview.text = "Rp. ${eachMenu.harga}"
        viewHolder.itemView.vieworderedmenu_nama_textview.text = eachMenu.nama
        viewHolder.itemView.vieworderedmenu_itemcount.text = "${pesanan[position]} x"

    }
}