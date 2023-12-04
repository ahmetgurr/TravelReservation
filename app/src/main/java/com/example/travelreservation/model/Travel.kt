package com.example.travelreservation.model

import com.google.gson.annotations.SerializedName

data class Travel(
    @SerializedName("cityFrom")
    var cityFrom: String? = null,
    @SerializedName("cityTo")
    var cityTo: String? = null,
    @SerializedName("distance")
    var distance: String? = null){
}