package com.example.mastersh.realtimeLocation.presenter

import com.example.mastersh.delivery.config.ConfigDeliveryAPI
import com.example.mastersh.delivery.model.ResponseDeliveryServer
import com.example.mastersh.realtimeLocation.config.ConfigLocationAPI
import com.example.mastersh.realtimeLocation.model.ResponseLocationServer
import com.example.mastersh.realtimeLocation.view.LocationView
import retrofit2.Call
import retrofit2.Response

class PresenterLocation(val locationView: LocationView) {

    // fungsi untuk mengirimkan update location ke server
    fun save_location_realtime(token: String,latitude: String,longitude: String){
        ConfigLocationAPI.getLocationService().save_location_realtime(token,latitude,longitude)
            .enqueue(object : retrofit2.Callback<ResponseLocationServer>{
                override fun onFailure(call: Call<ResponseLocationServer>, t: Throwable) {
                    locationView.gagalPOSTLOCATION(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResponseLocationServer>,
                    response: Response<ResponseLocationServer>
                ) {
                    if (response.isSuccessful && response.body()?.kode == 200){
                        locationView.suksesPOSTLOCATION(response.body()?.message ?:"")
                    } else {
                        locationView.gagalPOSTLOCATION(response.body()?.message ?:"")
                    }
                }
            })
    }
}