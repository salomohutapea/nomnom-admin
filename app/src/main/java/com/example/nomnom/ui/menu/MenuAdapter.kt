package com.example.nomnom.ui.menu

import android.annotation.SuppressLint
import com.example.nomnom.R
import com.example.nomnom.models.MenuModel
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.view_menu_item.view.*

class MenuAdapter(val data: MenuModel) : Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.view_menu_item
    }

    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {

        Picasso.get().load(data.imgUrl).resize(100, 100)
            .centerCrop().into(viewHolder.itemView.viewmenu_imageView)
        viewHolder.itemView.viewmenu_nama_textview.text = data.nama
        viewHolder.itemView.viewmenu_info_textview.text = data.info
        viewHolder.itemView.viewmenu_harga_textview.text = "Rp. ${data.harga}"

        //TODO: Implement setonclicklistener on edit and delete buttons

//        viewHolder.itemView.viewmenu_hapus_button.setOnClickListener {
//        }
//        viewHolder.itemView.viewmenu_ubah_button.setOnClickListener {
//        }
    }
}