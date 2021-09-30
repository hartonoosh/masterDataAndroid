package com.example.mastersh.delivery.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DataDN: Serializable {

    @field:SerializedName("id_note")
    val idNote: String? = null

    @field:SerializedName("nama_customer")
    val namaCustomer: String? = null

    @field:SerializedName("alamat_customer")
    val alamatCustomer: String? = null

    @field:SerializedName("total_item")
    val totalItem: String? = null

}