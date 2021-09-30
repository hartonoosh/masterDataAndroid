package com.example.mastersh.realtimeLocation.view

interface LocationView {
    // respon untuk POST data location realtime
    fun suksesPOSTLOCATION(msg: String)
    fun gagalPOSTLOCATION(msg : String)
}