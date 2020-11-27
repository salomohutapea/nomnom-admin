package com.example.nomnom.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderModel (
    @field:SerializedName("_id")
    val orderId: String? = null,

    @field:SerializedName("foods")
    val foods: Foods? = null,

    @field:SerializedName("noMeja")
    val tableNo: String? = null
) : Serializable

data class Foods (
    @field:SerializedName("idMenu")
    val menuId: List<String>? = null,

    @field:SerializedName("quantity")
    val quantity: List<String>? = null
) : Serializable