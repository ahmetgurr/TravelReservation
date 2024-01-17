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
    private lateinit var textViewName: String
    private lateinit var textViewSurname: String
    private lateinit var selectedCityFrom: String
    private lateinit var selectedCityTo: String

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
/*
        val textViewName = arguments?.getString("textViewName")
        val textViewSurname = arguments?.getString("textViewSurname")
        val textViewCityFrom = arguments?.getString("textViewCityFrom")
        val textViewCityTo = arguments?.getString("textViewCityTo")


        val textName: TextView = view.findViewById(R.id.textViewName)
        val textSurname: TextView = view.findViewById(R.id.textViewSurname)
        val textCityFrom: TextView = view.findViewById(R.id.text_cityFrom)
        val textCityTo: TextView = view.findViewById(R.id.textCityTo)


        textName.text = textViewName
        textSurname.text = textViewSurname
        textCityFrom.text = textViewCityFrom
        textCityTo.text = textViewCityTo



        // Verileri argümanlardan al
        val args = ChooseSeatFragmentArgs.fromBundle(requireArguments())
        selectedCityFrom = args.selectedCityFrom
        binding.textCityFrom.text = selectedCityFrom
        */
        arguments?.let {
            selectedCityFrom = it.getString("selectedCityFrom").toString()
            selectedCityTo = it.getString("selectedCityTo").toString()

            binding.textCityFrom.text = selectedCityFrom
            binding.textCityTo.text = selectedCityTo
        }

    }

}