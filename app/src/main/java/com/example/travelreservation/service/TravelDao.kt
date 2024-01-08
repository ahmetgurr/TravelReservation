package com.example.travelreservation.service

import com.example.travelreservation.model.Travel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


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
    @GET("ahmetgurr/JSONDataSet/main/travell.json")
    suspend fun getData(
        @Query("cityFrom") cityFrom: String?,
        @Query("cityTo") cityTo: String?
    ): Response<List<Travel>>
}
