package com.example.travelreservation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.travelreservation.R
import com.example.travelreservation.databinding.FragmentChooseSeatBinding

class ChooseSeatFragment : Fragment() {
    private lateinit var binding: FragmentChooseSeatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseSeatBinding.inflate(inflater, container, false)
        return binding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewName = arguments?.getString("textViewName")
        val textViewSurname = arguments?.getString("textViewSurname")
        val selectedCityFrom = arguments?.getString("selectedCityFrom")
        val selectedCityTo = arguments?.getString("selectedCityTo")

        val textName: TextView = view.findViewById(R.id.textViewName)
        val textSurname: TextView = view.findViewById(R.id.textViewSurname)
        val textSelectedCityFrom: TextView = view.findViewById(R.id.text_cityFrom)
        val textSelectedCityTo: TextView = view.findViewById(R.id.textCityTo)

        textName.text = textViewName
        textSurname.text = textViewSurname
        textSelectedCityFrom.text = selectedCityFrom
        textSelectedCityTo.text = selectedCityTo

    }

}