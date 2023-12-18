package com.example.travelreservation.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.travelreservation.R
import com.example.travelreservation.adapter.TravelRecyclerAdapter
import com.example.travelreservation.databinding.FragmentHomeBinding
import com.example.travelreservation.databinding.FragmentSettingBinding
import com.example.travelreservation.ui.login.LoginActivity
import com.example.travelreservation.ui.viewmodel.HomeFragmentViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var travelRecyclerAdapter: TravelRecyclerAdapter

    private val cities = arrayOf("Malatya", "İstanbul", "Ankara", "İzmir", "Bursa", "Antalya", "Adana", "Eskişehir", "Trabzon")



    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        binding.gecis.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToTravelListFragment()
            Navigation.findNavController(it).navigate(action)
            Toast.makeText(context, "Ticket List", Toast.LENGTH_SHORT).show()
        }

        binding.btnSearchFlight.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToTravelListFragment()
            Navigation.findNavController(it).navigate(action)
            Toast.makeText(context, "Ticket List", Toast.LENGTH_SHORT).show()
        }
        binding.deneme1.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDropdown()
            Navigation.findNavController(it).navigate(action)
            Toast.makeText(context, "Deneme Sayfası", Toast.LENGTH_SHORT).show()
        }

        binding.denemeMaps.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToMapsActivity()
            Navigation.findNavController(it).navigate(action)
            Toast.makeText(context, "Haritalar", Toast.LENGTH_SHORT).show()
        }


        //using spinner menu
        val spinner = binding.spinnerHours
        val spinner1 = binding.spinnerDate
        //val spinner2 = binding.planetsSpinnerAutoComplete

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