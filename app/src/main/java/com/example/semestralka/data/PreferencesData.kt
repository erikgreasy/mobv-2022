package com.example.semestralka.data

import android.content.Context

class PreferencesData private constructor(val context: Context) {
    val sharedObject = context.getSharedPreferences("mobvData", Context.MODE_PRIVATE)

    fun get(key: String): String? {
        return sharedObject.getString(key, null)
    }

    fun set(data: Map<String,String>) {
        val editor = sharedObject.edit()

        data.forEach { entry ->
            editor.putString(entry.key, entry.value)
        }

        editor.apply()
    }

    fun getLoggedUser(): LoggedUser? {
        val uid = get("loggedUser.uid")
        val access = get("loggedUser.access")
        val refresh = get("loggedUser.refresh")

        if(uid == null || access == null || refresh == null) {
            return null
        }

        return LoggedUser(uid, access, refresh)
    }

    fun removeLoggedUser() {
        sharedObject.edit().clear().commit()
    }

    fun setLoggedUser(userData: LoggedUser) {
        set(mapOf(
            "loggedUser.uid" to userData.uid,
            "loggedUser.access" to userData.access,
            "loggedUser.refresh" to userData.refresh
        ))
    }
    companion object {
        @Volatile
        private var INSTANCE: PreferencesData? = null

        fun getInstance(context: Context): PreferencesData =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: PreferencesData(context).also { INSTANCE = it }
            }
    }
}