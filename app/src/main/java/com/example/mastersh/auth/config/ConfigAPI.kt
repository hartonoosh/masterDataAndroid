package com.example.mastersh.auth.config

import com.example.mastersh.auth.model.ResponseServerType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

object ConfigAPI {

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
            //.baseUrl("http://sample.srihartono.com/android/")
            .client(getInterCeptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getLoginService() = getRetrofit().create(LoginService::class.java)
}

interface LoginService {

    // Link untuk POST LOGIN USER  melalui API
    @FormUrlEncoded
    @POST("auth/login")
    fun login_user(@Field("email") email : String,
                   @Field("password") password : String) : Call<ResponseServerType>

    // Link untuk POST REGISTER USER melalui API
    @FormUrlEncoded
    @POST("auth/register")
    fun register_user(@Field("username") username : String,
                   @Field("email") email : String,
                   @Field("password") kemasan: String) : Call<ResponseServerType>

    @FormUrlEncoded
    @POST("auth/logout")
    fun logout_user(@Field("token") token: String): Call<ResponseServerType>

}