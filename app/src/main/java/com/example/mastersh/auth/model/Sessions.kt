package com.example.mastersh.auth.model

import android.content.Context
import android.content.SharedPreferences

class Sessions(context: Context) {
    var settings: SharedPreferences
    var context: Context
    fun createSession(key: String?, value: String?) {
        val editor = settings.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun removeSession(key: String?){
        val editor = settings.edit()
        editor.remove(key)
        editor.commit()
    }

    fun getSession(key: String?): String? {
        val settings: SharedPreferences = context.getSharedPreferences(Variables().SESSION_NAME, 0)
        return settings.getString(key, "")
    }

    fun getSessionToken(key: String?, value: String?): String? {
        val settings: SharedPreferences = context.getSharedPreferences(Variables().SESSION_NAME, 0)
        return settings.getString(key, value)
    }

    init {
        this.context = context
        settings = context.getSharedPreferences(
            Variables().SESSION_NAME,
            0
        ) //we have to define session name here
    }
}