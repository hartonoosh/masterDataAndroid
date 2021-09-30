package com.example.mastersh.auth.model

import com.google.gson.annotations.SerializedName

class ResponseServer {
    // menerima respon message dari server
    @field: SerializedName("message")
    val message: String? = null

    // menerima respon kode dari server
    @field:SerializedName("kode")
    val kode: Int? = null

    @field:SerializedName("token")
    val token: String? = null
}