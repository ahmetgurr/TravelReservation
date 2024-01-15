package com.example.travelreservation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelreservation.R
import com.example.travelreservation.databinding.FragmentAddPassengerBinding


class AddPassengerFragment : Fragment() {

    private lateinit var binding: FragmentAddPassengerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_passenger, container, false)
    }

}