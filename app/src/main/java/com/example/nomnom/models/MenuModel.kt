package com.example.nomnom.models

import com.google.gson.annotations.SerializedName

data class MenuModel (
    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("imgUrl")
    val imgUrl: String? = null,

    @field:SerializedName("harga")
    val harga: String? = null,

    @field:SerializedName("info")
    val info: String? = null
)
