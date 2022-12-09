package com.example.semestralka.api

import android.content.Context
import android.util.Log
import com.example.semestralka.data.LoggedUser
import com.example.semestralka.data.PreferencesData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(val context: Context): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            Log.e("authenticator", "mame 401")

            val userItem = PreferencesData.getInstance(context).getLoggedUser()

            if (userItem == null) {
                PreferencesData.getInstance(context).removeLoggedUser()
                return null
            }

            GlobalScope.launch {

                val tokenResponse = RetrofitInstance.getInstance(context).api.refresh(
                    RefreshData(
                        userItem.refresh
                    )
                )

                if(!tokenResponse.isSuccessful) {
                    PreferencesData.getInstance(context).removeLoggedUser()
                    null
                }

                tokenResponse.body()?.let {
                    PreferencesData.getInstance(context).setLoggedUser(
                        LoggedUser(
                            it.uid,
                            it.access,
                            it.refresh
                        ))
                    response.request.newBuilder()
                        .header("authorization", "Bearer ${it.access}")
                        .build()
                }
            }
        }

        return null
    }
}