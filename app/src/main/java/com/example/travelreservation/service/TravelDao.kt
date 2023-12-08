package com.example.travelreservation.service

import com.example.travelreservation.model.Travel
import retrofit2.Response
import retrofit2.http.GET

import retrofit2.Call


//[ Base URL: test.api.amadeus.com/v2 ]
// amadeus.travel.analytics.airTraffic.traveled
// amadeus.travel.analytics.airTraffic.booked
//TravelAPIKey: 9mFA97Zrv3QLptK2xvAoZ1cAAYG9k5lx
//TravelAPISecret:

// get, post, update, delete -> Retrofit
//https://github.com/ahmetgurr/JSONDataSet/blob/main/travel.json
//https://raw.githubusercontent.com/ahmetgurr/JSONDataSet/main/travel.json
//https://raw.githubusercontent.com/
//ahmetgurr/JSONDataSet/main/travel.json


interface TravelDao {

    //@GET("/shopping/flight-offers")
    //suspend fun travel(): Response<Travel>

    //@GET("ahmetgurr/JSONDataSet/main/travel.json")//asıl json dosyası
    @GET("ahmetgurr/JSONDataSet/main/travell.json")
    suspend fun getData(): Response<List<Travel>>

}