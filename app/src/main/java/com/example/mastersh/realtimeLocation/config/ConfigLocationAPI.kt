package com.example.mastersh.realtimeLocation.config

import com.example.mastersh.delivery.model.ResponseDeliveryServer
import com.example.mastersh.realtimeLocation.model.ResponseLocationServer
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object ConfigLocationAPI {

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

    fun getLocationService() = getRetrofit().create(LocationService::class.java)
}

interface LocationService {

    // Link untuk POST LIST DELIVERY ORDER  melalui API
    @FormUrlEncoded
    @POST("realtime")
    fun save_location_realtime(@Field("token") token: String,
                                @Field("latitude") latitude: String,
                                @Field("longitude") longitude: String) : Call<ResponseLocationServer>
}