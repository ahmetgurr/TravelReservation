package com.example.travelreservation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.travelreservation.R
import com.example.travelreservation.databinding.FragmentHomeBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var cities: Array<String>
    private lateinit var editTextBirthDay: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cities = resources.getStringArray(R.array.cities_array)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        //Search Fligt Button
        binding.btnSearchFlight.setOnClickListener {
            val selectedCityFrom = binding.planetsSpinnerAutoCompleteFrom.text.toString()
            val selectedCityTo = binding.planetsSpinnerAutoCompleteTo.text.toString()

            val action = HomeFragmentDirections.actionHomeFragmentToTravelListFragment(
                selectedCityFrom,
                selectedCityTo
            )
            Navigation.findNavController(it).navigate(action)
            Toast.makeText(context, "Ticket List", Toast.LENGTH_SHORT).show()
        }
        //For the try Button
        binding.dropdownFragment.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDropdown()
            Navigation.findNavController(it).navigate(action)
            Toast.makeText(context, "Dropdown Sayfası", Toast.LENGTH_SHORT).show()
        }


        // DropDown menu for the "from" and "to" cities
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, cities)
        binding.planetsSpinnerAutoCompleteFrom.setAdapter(adapter)
        binding.planetsSpinnerAutoCompleteTo.setAdapter(adapter)

        // Item click listener for "from" AutoCompleteTextView
        binding.planetsSpinnerAutoCompleteFrom.setOnItemClickListener { parent, _, position, _ ->
            val selectedCity = parent.getItemAtPosition(position).toString()
            // Seçilen şehirle ilgili işlemleri burada yapılacak...
        }

        // Show dropdown when "from" AutoCompleteTextView is clicked
        binding.planetsSpinnerAutoCompleteFrom.setOnClickListener {
            binding.planetsSpinnerAutoCompleteFrom.showDropDown()
        }

        // Item click listener for "to" AutoCompleteTextView
        binding.planetsSpinnerAutoCompleteTo.setOnItemClickListener { parent, _, position, _ ->
            val selectedCity = parent.getItemAtPosition(position).toString()
            // Seçilen şehirle ilgili işlemleri burada yapılacak...
        }

        // Show dropdown when "to" AutoCompleteTextView is clicked
        binding.planetsSpinnerAutoCompleteTo.setOnClickListener {
            binding.planetsSpinnerAutoCompleteTo.showDropDown()
        }

        editTextBirthDay = binding.datePickerId
        setupDatePicker()


    }

    private fun setupDatePicker() {
        editTextBirthDay.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener { selection ->
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(Date(selection))
            editTextBirthDay.setText(formattedDate)
        }
        picker.show(parentFragmentManager, picker.toString())
    }


}