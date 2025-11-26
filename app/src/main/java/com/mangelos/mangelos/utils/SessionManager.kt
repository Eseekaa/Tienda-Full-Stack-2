package com.mangelos.mangelos.utils

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("MangelOS_Session", Context.MODE_PRIVATE)

    fun saveSession(token: String, role: String?) {
        val editor = prefs.edit()
        editor.putString("TOKEN", token)
        editor.putString("ROLE", role)
        editor.apply()
    }

    fun getToken(): String? = prefs.getString("TOKEN", null)
    fun getRole(): String? = prefs.getString("ROLE", null)

    fun logout() {
        prefs.edit().clear().apply()
    }
}