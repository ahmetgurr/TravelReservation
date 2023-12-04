package com.example.travelreservation.service

import android.content.Context
import com.amadeus.android.Amadeus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import android.util.Log;
import com.amadeus.android.ApiResult


class RetrofitClient(private val context: Context) {

/*
    companion object {
        fun getClient(baseUrl: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//gson ile json verilerini parse etmek için
                .build()
        }
    }
 */

    val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.Main + job)

    val amadeus = Amadeus.Builder(context)
        .setClientId("9mFA97Zrv3QLptK2xvAoZ1cAAYG9k5lx")
        .setClientSecret("qtCpFITduopHHFme")
        .build()


}