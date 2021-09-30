package com.example.mastersh.delivery.model

import com.google.gson.annotations.SerializedName

class ResponseDeliveryServer {
    // menerima respon message dari server
    @field: SerializedName("message")
    val message: String? = null

    // menerima respon kode dari server
    @field:SerializedName("kode")
    val kode: Int? = null

    // menerima respon data dari server
    @field:SerializedName("data")
    val data: List<DataRespon>? = null
}