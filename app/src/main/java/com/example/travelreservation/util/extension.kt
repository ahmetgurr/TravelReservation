package com.example.travelreservation.util

import android.view.View
import androidx.navigation.Navigation

fun Navigation.nextPage(id:Int, it: View) {
    findNavController(it).navigate(id)
}