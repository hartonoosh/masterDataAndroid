package com.example.mastersh.delivery.view

import com.example.mastersh.delivery.model.DataDN
import com.example.mastersh.delivery.model.DataRespon

interface DeliveryView {
    // respon untuk GET data Delivery Order
    fun suksesGETDO(data: List<DataRespon>?)
    fun gagalGETDO(msg : String)

    // respon untuk GET data Delivery Note
    fun suksesGETDN(data: List<DataRespon>?)
    fun gagalGETDN(msg: String)
}