package com.example.travelreservation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelreservation.R
import com.example.travelreservation.adapter.TravelRecyclerAdapter
import com.example.travelreservation.databinding.FragmentTravelListBinding
import com.example.travelreservation.model.Travel
import com.example.travelreservation.service.TravelDao
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TravelListFragment : Fragment() {

    private lateinit var binding: FragmentTravelListBinding

    //https://raw.githubusercontent.com/ahmetgurr/JSONDataSet/main/travell.json
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var travelModel: ArrayList<Travel>? = null
    private var recyclerViewAdapter: TravelRecyclerAdapter? = null
    private var job : Job? = null
    private var compositeDisposable : CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_travel_list, container, false)
        binding = FragmentTravelListBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedCityFrom = arguments?.getString("selectedCityFrom")
        val selectedCityTo = arguments?.getString("selectedCityTo")

        loadData(selectedCityFrom, selectedCityTo)

        compositeDisposable = CompositeDisposable()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager

    }

    private fun loadData(selectedCityFrom: String?, selectedCityTo: String?) {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TravelDao::class.java)

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            // Handle the exception here
            Log.e("TravelListFragment", "Error fetching travel data:", throwable)
        }

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.getData(cityFrom = null, cityTo = null)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { travelList ->
                        // If both selectedCityFrom and selectedCityTo are provided, filter the list
                        val filteredList = if (selectedCityFrom != null && selectedCityTo != null) {
                            travelList.filter { travel ->
                                travel.cityFrom == selectedCityFrom && travel.cityTo == selectedCityTo
                            }
                        } else {
                            // If any of them is null, use the original list
                            travelList
                        }

                        recyclerViewAdapter = TravelRecyclerAdapter(ArrayList(filteredList), this@TravelListFragment)
                        binding.recyclerView.adapter = recyclerViewAdapter
                    }
                } else {
                    // Handle the error response
                    Toast.makeText(activity, "Error fetching travel data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    fun onItemClick(travelModel: Travel) {
        Toast.makeText(activity,"Clicked: ${travelModel.cityFrom} - ${travelModel.cityTo}",Toast.LENGTH_SHORT).show()
    }




}
