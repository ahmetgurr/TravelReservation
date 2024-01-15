package com.example.travelreservation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.travelreservation.R
import com.example.travelreservation.databinding.FragmentPassengerInfoBinding
import com.example.travelreservation.util.nextPage

class PassengerInfoFragment : Fragment() {

    private lateinit var binding: FragmentPassengerInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPassengerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // TravelListFragment'den taşınan verileri al
        val selectedItemId = arguments?.getString("selectedItemId")
        val selectedCityFrom = arguments?.getString("selectedCityFrom")
        val selectedCityTo = arguments?.getString("selectedCityTo")

        // TextView'ları bul
        val textCityFrom: TextView = view.findViewById(R.id.text_cityFrom)
        val textCityTo: TextView = view.findViewById(R.id.textCityTo)

        // TextView'lara verileri ekle
        textCityFrom.text = selectedCityFrom
        textCityTo.text = selectedCityTo

        // Add Passenger Button
        binding.btnAddPassengers.setOnClickListener{
            Navigation.nextPage(R.id.action_passengerInfoFragment_to_addPassengerFragment,it)
            Toast.makeText(context, "Add Passenger", Toast.LENGTH_SHORT).show()
        }

    }

}