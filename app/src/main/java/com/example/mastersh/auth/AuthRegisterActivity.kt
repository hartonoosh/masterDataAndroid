package com.example.mastersh.auth

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
import com.example.mastersh.auth.presenter.PresenterAuth
import com.example.mastersh.auth.view.AuthView
import org.jetbrains.anko.sdk27.coroutines.onClick

class AuthRegisterActivity : AppCompatActivity(), AuthView {
    private lateinit var presenterAuth: PresenterAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_register)

        presenterAuth = PresenterAuth(this)

        val registerUsername = findViewById<EditText>(R.id.etAuthRegisterUsername)
        val registerEmail = findViewById<EditText>(R.id.etAuthRegisterEmail)
        val registerPassword = findViewById<EditText>(R.id.etAuthRegisterPassword)
        val registerPasswordRepeat = findViewById<EditText>(R.id.etAuthRegisterPasswordRepeat)

        val txtAuthRegisterAlredyAccount = findViewById<TextView>(R.id.txtAuthRegisterAlredyAccount)
        txtAuthRegisterAlredyAccount.setOnClickListener {
            startActivity(Intent(this, AuthLoginActivity::class.java))
        }

        val btnAuthRegister = findViewById<Button>(R.id.btnAuthRegister)
        btnAuthRegister.setOnClickListener {
            CustomLoading.showLoading(this)// berguna untuk memulai animasi loading

            // deklarasi kolom edit text
            val username = registerUsername.text.toString().trim()
            val email = registerEmail.text.toString().trim()
            val password = registerPassword.text.toString().trim()
            val passwordRepeat = registerPasswordRepeat.text.toString().trim()

            // mengecek validasi dari kolom input
            if (checkValidationRegister(username,email,password,passwordRepeat)){
                presenterAuth.register_user(
                    username,
                    email,
                    password
                )
            }
        }
    }

    override fun checkValidationRegister(username: String, email: String, password: String, passwordRepeat: String): Boolean {
        val registerUsername = findViewById<EditText>(R.id.etAuthRegisterUsername)
        val registerEmail = findViewById<EditText>(R.id.etAuthRegisterEmail)
        val registerPassword = findViewById<EditText>(R.id.etAuthRegisterPassword)
        val registerPasswordRepeat = findViewById<EditText>(R.id.etAuthRegisterPasswordRepeat)
        if (username.isEmpty()){
            registerUsername.error = "Username tidak boleh kosong"
            registerUsername.requestFocus()
        }else if(email.isEmpty()){
            registerEmail.error = "E-mail tidak boleh kosong"
            registerEmail.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            registerEmail.error = "E-mail anda tidak valid"
            registerEmail.requestFocus()
        }else if (password.isEmpty()){
            registerPassword.error = "Password tidak boleh kosong"
            registerPassword.requestFocus()
        }else if (passwordRepeat.isEmpty()){
            registerPasswordRepeat.error = "Password tidak boleh kosong"
            registerPasswordRepeat.requestFocus()
        }else if (password != passwordRepeat){
            registerPassword.error = "Password tidak sama"
            registerPasswordRepeat.error = "Password tidak sama"
        }else{
            return true
        }
        CustomLoading.hideLoading() // berguna untuk menghentikan animasi loading
        return false
    }

    override fun suksesREGISTER(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        startActivity(Intent(this, AuthLoginActivity::class.java))
        finishAffinity()
    }

    override fun errorREGISTER(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        CustomLoading.hideLoading() // berguna untuk menghentikan animasi loading
    }

    override fun suksesLOGOUT(msg: String) {
        TODO("Not yet implemented")
    }

    override fun errorLOGOUT(msg: String) {
        TODO("Not yet implemented")
    }

    override fun suksesLOGIN(msg: String, token: String) {
        TODO("Not yet implemented")
    }

    override fun errorLOGIN(msg: String) {
        TODO("Not yet implemented")
    }

    override fun checkValidationLogin(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }


}