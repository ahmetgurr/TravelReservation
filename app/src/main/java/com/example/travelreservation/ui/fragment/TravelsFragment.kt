package com.example.travelreservation.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.travelreservation.R
import com.example.travelreservation.adapter.PlaceAdapter
import com.example.travelreservation.databinding.FragmentTravelsBinding
import com.example.travelreservation.model.Place
import com.example.travelreservation.roomdb.PlaceDatabase
import com.example.travelreservation.ui.MapsActivity
import com.example.travelreservation.util.nextPage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.ArrayList

class TravelsFragment : Fragment() {
    private lateinit var binding: FragmentTravelsBinding
    private val mDisposable = CompositeDisposable()
    private lateinit var places: ArrayList<Place>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTravelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        places = ArrayList()

        val db = Room.databaseBuilder(requireContext(), PlaceDatabase::class.java, "Places")
            //.allowMainThreadQueries()
            .build()

        val placeDao = db.placeDao()

        mDisposable.add(placeDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))

        binding.fab.setOnClickListener{
            Navigation.nextPage(R.id.action_travelsFragment_to_mapsActivity,it)
        }

    }

    private fun handleResponse(placeList: List<Place>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val placeAdapter = PlaceAdapter(placeList)
        binding.recyclerView.adapter = placeAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.travel_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_place) {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            intent.putExtra("info", "new")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.clear()
    }
}