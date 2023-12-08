package com.example.travelreservation.model

import com.google.gson.annotations.SerializedName
/*
data class Travel(
    @SerializedName("cityFrom")
    var cityFrom: String? = null,
    @SerializedName("cityTo")
    var cityTo: String? = null,
    @SerializedName("distance")
    var distance: String? = null){
}
 */

data class Travel(
    @SerializedName("cityFrom")
    var cityFrom: String? = null,
    @SerializedName("cityTo")
    var cityTo: String? = null,
    @SerializedName("distance")
    var distance: String? = null,
    @SerializedName("hours")
    var hours: String? = null,
    @SerializedName("price")
    var price: String? = null
)
{
}