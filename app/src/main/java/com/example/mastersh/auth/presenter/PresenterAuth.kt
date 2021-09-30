package com.example.mastersh.auth.presenter

import com.example.mastersh.auth.config.ConfigAPI
import com.example.mastersh.auth.model.ResponseServerType
import com.example.mastersh.auth.view.AuthView
import retrofit2.Call
import retrofit2.Response

class PresenterAuth(val authView: AuthView) {

    // fungsi untuk LOGIN USER dari android
    fun login_user(email: String,password: String){
        ConfigAPI.getLoginService().login_user(email, password)
            .enqueue(object : retrofit2.Callback<ResponseServerType>{
                override fun onFailure(call: Call<ResponseServerType>, t: Throwable) {
                    authView.errorLOGIN(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResponseServerType>,
                    response: Response<ResponseServerType>
                ) {
                    if (response.isSuccessful && response.body()?.kode == 200){
                        authView.suksesLOGIN(response.body()?.message ?:"",response.body()?.token ?:"")
                    } else {
                        authView.errorLOGIN(response.body()?.message ?:"")
                    }
                }
            })
    }

    // fungsi untuk REGISTER USER dari android
    fun register_user(username: String,email: String,password: String){
        ConfigAPI.getLoginService().register_user(username,email, password)
            .enqueue(object : retrofit2.Callback<ResponseServerType>{
                override fun onFailure(call: Call<ResponseServerType>, t: Throwable) {
                    authView.errorREGISTER(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResponseServerType>,
                    response: Response<ResponseServerType>
                ) {
                    if (response.isSuccessful && response.body()?.kode == 200){
                        authView.suksesREGISTER(response.body()?.message ?:"")
                    } else {
                        authView.errorREGISTER(response.body()?.message ?:"")
                    }
                }
            })
    }

    // fungsi untuk REGISTER USER dari android
    fun logout_user(token: String){
        ConfigAPI.getLoginService().logout_user(token)
            .enqueue(object : retrofit2.Callback<ResponseServerType>{
                override fun onFailure(call: Call<ResponseServerType>, t: Throwable) {
                    authView.errorLOGOUT(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResponseServerType>,
                    response: Response<ResponseServerType>
                ) {
                    if (response.isSuccessful && response.body()?.kode == 200){
                        authView.suksesLOGOUT(response.body()?.message ?:"")
                    } else {
                        authView.errorLOGOUT(response.body()?.message ?:"")
                    }
                }
            })
    }
}