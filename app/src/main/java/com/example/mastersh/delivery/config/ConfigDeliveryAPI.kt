package com.example.mastersh.delivery.config

import com.example.mastersh.auth.model.ResponseServerType
import com.example.mastersh.delivery.model.ResponseDeliveryServer
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object ConfigDeliveryAPI {

    private fun getInterCeptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://api.srihartono.com/")
            .client(getInterCeptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getDeliveryService() = getRetrofit().create(DeliveryService::class.java)
}

interface DeliveryService {

    // Link untuk POST LIST DELIVERY ORDER  melalui API
    @FormUrlEncoded
    @POST("list-do")
    fun list_delivery_order(@Field("token") token: String) : Call<ResponseDeliveryServer>

    // Link untuk POST LIST DELIVERY NOTE melalui API
    @FormUrlEncoded
    @POST("list-dn")
    fun list_delivery_note(@Field("token") token: String,
                            @Field("id_order") id_order : String) : Call<ResponseDeliveryServer>
    // Link untuk POST LIST ITEM melalui API
    @FormUrlEncoded
    @POST("list-item")
    fun list_item(@Field("token") token: String,
                    @Field("id_note") id_note: String): Call<ResponseDeliveryServer>

    @Multipart
    @POST("upload-image-customer")
    fun upload_image_customer(@Part token: MultipartBody.Part,
                              @Part id_note: MultipartBody.Part,
                              @Part image: MultipartBody.Part):Call<ResponseDeliveryServer>

}