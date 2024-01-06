package com.example.travelreservation.util

import android.view.View
import androidx.navigation.Navigation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

fun Navigation.nextPage(id:Int, it: View) {
    findNavController(it).navigate(id)
}
