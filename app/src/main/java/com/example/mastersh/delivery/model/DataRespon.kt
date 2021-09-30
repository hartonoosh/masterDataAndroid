package com.example.mastersh.delivery.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DataRespon: Serializable {

    @field:SerializedName("id_order")
    val idOrder: String? = null

    @field:SerializedName("no_delivery_order")
    val noDeliveryOrder: String? = null

    @field:SerializedName("nama_driver")
    val namaDriver: String? = null

    @field:SerializedName("nopol_driver")
    val nopolDriver: String? = null

    @field:SerializedName("total_delivery_note")
    val totalDeliveryNote: String? = null


    @field:SerializedName("id_note")
    val idNote: String? = null

    @field:SerializedName("nama_customer")
    val namaCustomer: String? = null

    @field:SerializedName("alamat_customer")
    val alamatCustomer: String? = null

    @field:SerializedName("telp_customer")
    val telpCustomer: String? = null

    @field:SerializedName("status")
    val statusCustomer: String? = null

    @field:SerializedName("total_item")
    val totalItem: String? = null

}