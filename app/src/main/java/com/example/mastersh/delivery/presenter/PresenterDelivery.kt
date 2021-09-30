package com.example.mastersh.delivery.presenter
import com.example.mastersh.delivery.config.ConfigDeliveryAPI
import com.example.mastersh.delivery.model.ResponseDeliveryServer
import com.example.mastersh.delivery.view.DeliveryView
import retrofit2.Call
import retrofit2.Response

class PresenterDelivery(val deliveryView: DeliveryView) {
    // fungsi untuk mendapatkan list data Delivery Order dari server
    fun get_data_do(token: String){
        ConfigDeliveryAPI.getDeliveryService().list_delivery_order(token)
            .enqueue(object : retrofit2.Callback<ResponseDeliveryServer>{
                override fun onFailure(call: Call<ResponseDeliveryServer>, t: Throwable) {
                    deliveryView.gagalGETDO(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResponseDeliveryServer>,
                    response: Response<ResponseDeliveryServer>
                ) {
                    if (response.isSuccessful && response.body()?.kode == 200){
                        val data = response.body()?.data
                        deliveryView.suksesGETDO(data)
                    } else {
                        deliveryView.gagalGETDO(response.body()?.message ?:"")
                    }
                }
            })
    }

    // fungsi untuk mendapatkan list data Delivery Order dari server
    fun get_data_dn(token: String,id_order:String){
        ConfigDeliveryAPI.getDeliveryService().list_delivery_note(token,id_order)
            .enqueue(object : retrofit2.Callback<ResponseDeliveryServer>{
                override fun onFailure(call: Call<ResponseDeliveryServer>, t: Throwable) {
                    deliveryView.gagalGETDN(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResponseDeliveryServer>,
                    response: Response<ResponseDeliveryServer>
                ) {
                    if (response.isSuccessful && response.body()?.kode == 200){
                        val data = response.body()?.data
                        deliveryView.suksesGETDN(data)
                    } else {
                        deliveryView.gagalGETDN(response.body()?.message ?:"")
                    }
                }
            })
    }
}