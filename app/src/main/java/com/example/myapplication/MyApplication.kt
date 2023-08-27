package com.example.myapplication

import android.app.Application
import android.util.Log
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(context = this)?.let { firebaseApp ->
            Firebase.appCheck(firebaseApp).installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance(),
            )

            Firebase.appCheck(firebaseApp).setTokenAutoRefreshEnabled(true)
            Firebase.appCheck(firebaseApp).addAppCheckListener {
                Log.d("AppCheck", "Token: ${it.token.takeLast(20)}")
                Log.d("AppCheck", "Expires in ${(it.expireTimeMillis - Date().time).milliseconds.inWholeMinutes} minutes")
            }

            Firebase.appCheck(firebaseApp).getAppCheckToken(false)
        }
    }
}
