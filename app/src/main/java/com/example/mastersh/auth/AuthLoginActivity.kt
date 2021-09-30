package com.example.mastersh.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mastersh.CustomLoading
import com.example.mastersh.R
import com.example.mastersh.auth.config.ConfigAPI
import com.example.mastersh.auth.model.ResponseServerType
import com.example.mastersh.auth.model.SessionManager
import com.example.mastersh.auth.model.Sessions
import com.example.mastersh.auth.presenter.PresenterAuth
import com.example.mastersh.auth.view.AuthView
import org.jetbrains.anko.sdk27.coroutines.onClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthLoginActivity : AppCompatActivity(), AuthView {

    private lateinit var sessionManager: SessionManager
    private lateinit var presenterAuth: PresenterAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_login)

        sessionManager = SessionManager(this)
        presenterAuth = PresenterAuth(this)

        val loginEmail = findViewById<EditText>(R.id.edAuthLoginEmail)
        val loginPassword = findViewById<EditText>(R.id.edAuthLoginPassword)

        val txtDontHaveAccount = findViewById<TextView>(R.id.txtDontHaveAccount)
        txtDontHaveAccount.setOnClickListener {
            startActivity(Intent(this, AuthRegisterActivity::class.java))
            //finish()
        }

        val btnAuthLogin = findViewById<Button>(R.id.btnAuthLogin)
        btnAuthLogin.setOnClickListener {
            CustomLoading.showLoading(this)// berguna untuk memulai animasi loading

            // deklarasi kolom edit text
            val email = loginEmail.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            // mengecek validasi dari kolom input
            if (checkValidationLogin(email,password)){
                presenterAuth.login_user(
                    email,
                    password,
                )
            }
        }
    }

    override fun checkValidationLogin(email: String, password: String): Boolean {
        val loginEmail = findViewById<EditText>(R.id.edAuthLoginEmail)
        val loginPassword = findViewById<EditText>(R.id.edAuthLoginPassword)
        if (email.isEmpty()){
            loginEmail.error = "E-mail tidak boleh kosong"
            loginEmail.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginEmail.error = "E-mail anda tidak valid"
            loginEmail.requestFocus()
        }else if (password.isEmpty()){
            loginPassword.error = "Password tidak boleh kosong"
            loginPassword.requestFocus()
        }else{
            return true
        }
        CustomLoading.hideLoading() // berguna untuk menghentikan animasi loading
        return false
    }

    override fun suksesLOGIN(msg: String, token: String) {
        //simpan session dengan kode di bawah ini
        startActivity(Intent(this, AuthMainActivity::class.java))
        finishAffinity()
        CustomLoading.hideLoading() // berguna untuk menghentikan animasi loading
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        sessionManager.saveAuthToken(token)
    }

    override fun errorLOGIN(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        CustomLoading.hideLoading() // berguna untuk menghentikan animasi loading
    }

    override fun suksesREGISTER(msg: String) {
        TODO("Not yet implemented")
    }

    override fun errorREGISTER(msg: String) {
        TODO("Not yet implemented")
    }

    override fun suksesLOGOUT(msg: String) {
        TODO("Not yet implemented")
    }

    override fun errorLOGOUT(msg: String) {
        TODO("Not yet implemented")
    }

    override fun checkValidationRegister(
        nama: String,
        email: String,
        password: String,
        passwordRepeat: String
    ): Boolean {
        TODO("Not yet implemented")
    }
}