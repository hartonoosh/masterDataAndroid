package com.example.mastersh.auth.view

interface AuthView {
    // respon untuk LOGIN
    fun suksesLOGIN(msg: String,token:String)
    fun errorLOGIN(msg: String)

    // respon untuk REGISTER
    fun suksesREGISTER(msg: String)
    fun errorREGISTER(msg: String)

    // respon untuk REGISTER
    fun suksesLOGOUT(msg: String)
    fun errorLOGOUT(msg: String)

    fun checkValidationRegister(
        nama: String,
        email: String,
        password: String,
        passwordRepeat: String
    ): Boolean

    fun checkValidationLogin(email: String, password: String): Boolean
}