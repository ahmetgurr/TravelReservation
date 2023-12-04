package com.example.travelreservation.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.travelreservation.R
import com.example.travelreservation.adapter.TravelRecyclerAdapter
import com.example.travelreservation.databinding.FragmentHomeBinding
import com.example.travelreservation.ui.login.LoginActivity
import com.example.travelreservation.ui.viewmodel.HomeFragmentViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var travelRecyclerAdapter: TravelRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
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

    }
}