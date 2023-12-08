package com.example.travelreservation.repository

import com.example.travelreservation.service.TravelDao

//DAO=Data Access Object
//DAO'ya erişim sağlayan sınıf
class TravelRepository(var tdao : TravelDao) {

    //seyahatleri çekmekxx
    //suspend fun getTravel() = tdao.travel()
}