package com.example.nomnom.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SimpleResponse(
    @field:SerializedName("message")
    val message: String? = null
) : Serializable