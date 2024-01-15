package com.example.travelreservation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.travelreservation.R
import com.example.travelreservation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var cities: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        cities = resources.getStringArray(R.array.cities_array)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        //Search Fligt Button
        binding.btnSearchFlight.setOnClickListener {
            // Get selected cities from AutoCompleteTextViews
            val selectedCityFrom = binding.planetsSpinnerAutoCompleteFrom.text.toString()
            val selectedCityTo = binding.planetsSpinnerAutoCompleteTo.text.toString()

            // Create an action with selected cities
            val action = HomeFragmentDirections.actionHomeFragmentToTravelListFragment(
                selectedCityFrom,
                selectedCityTo
            )
            // Navigate with the action
            Navigation.findNavController(it).navigate(action)

            Toast.makeText(context, "Ticket List", Toast.LENGTH_SHORT).show()
        }

        //Maps Button
        binding.denemeMaps.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToMapsActivity()
            Navigation.findNavController(it).navigate(action)
            Toast.makeText(context, "Haritalar", Toast.LENGTH_SHORT).show()
        }


        //For the try Button
        binding.dropdownFragment.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDropdown()
            Navigation.findNavController(it).navigate(action)
            Toast.makeText(context, "Dropdown Sayfası", Toast.LENGTH_SHORT).show()
        }


        //using spinner menu
        val spinner = binding.spinnerHours
        val spinner1 = binding.spinnerDate

        //adapter for the "hours_days"
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.hours_days,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        //adapter for the "week_days"
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.week_days,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner1.adapter = adapter
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

    }
}