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
//https://raw.githubusercontent.com/ahmetgurr/JSONDataSet/main/travell.json
class TravelListFragment : Fragment() {
    private lateinit var binding: FragmentTravelListBinding
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var recyclerViewAdapter: TravelRecyclerAdapter? = null
    private var job : Job? = null
    private var compositeDisposable : CompositeDisposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_travel_list, container, false)
        binding = FragmentTravelListBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedCityFrom = arguments?.getString("selectedCityFrom")
        val selectedCityTo = arguments?.getString("selectedCityTo")
        val selectedDate = arguments?.getString("selectedDate")
        loadData(selectedCityFrom, selectedCityTo)

        compositeDisposable = CompositeDisposable()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager

        // TravelRecyclerAdapter'ı oluştur ve seçilen tarih bilgisini atayın
        recyclerViewAdapter = selectedDate?.let { TravelRecyclerAdapter(ArrayList(), it, this) }
        binding.recyclerView.adapter = recyclerViewAdapter



    }

    //cityFrom ve cityTo ile verileri yükleyen loadData() fonksiyonu
    private fun loadData(selectedCityFrom: String?, selectedCityTo: String?) {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TravelDao::class.java)

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e("TravelListFragment", "Error fetching travel data:", throwable)
        }

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.getData(cityFrom = null, cityTo = null)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { travelList ->
                        val filteredList = if (selectedCityFrom != null && selectedCityTo != null) {
                            travelList.filter { travel ->
                                travel.cityFrom == selectedCityFrom && travel.cityTo == selectedCityTo
                            }
                        } else {
                            travelList
                        }

                        recyclerViewAdapter?.travelList = ArrayList(filteredList)
                        recyclerViewAdapter?.notifyDataSetChanged()

                    }
                } else {
                    Toast.makeText(activity, "Error fetching travel data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //cardview'a tıklandığında çalışan onItemClick() fonksiyonu
    fun onItemClick(travelModel: Travel) {
        Toast.makeText(activity,"Clicked: ${travelModel.cityFrom} - ${travelModel.cityTo} - ${travelModel.id}",Toast.LENGTH_SHORT).show()
    }
}
