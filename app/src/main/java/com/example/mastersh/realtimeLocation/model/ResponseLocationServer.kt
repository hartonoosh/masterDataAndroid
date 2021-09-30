package com.example.mastersh.realtimeLocation.model

import com.example.mastersh.delivery.model.DataRespon
import com.google.gson.annotations.SerializedName

class ResponseLocationServer {
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