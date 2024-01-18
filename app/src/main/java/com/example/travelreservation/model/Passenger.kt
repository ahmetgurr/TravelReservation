package com.example.travelreservation.model

data class Passenger(
    val id: String? = null,
    val Surname: String = "",
    val Name: String = "",
    val BirthDay: String = "",
    val Phone: String = "",
    val Gender: String = "",
    val isSelected: Boolean = false
)
