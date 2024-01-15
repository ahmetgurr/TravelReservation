package com.example.travelreservation.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
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

    fun setSeatLayout(context: Context, seatCount: Int) {
        val seatGridLayout = GridLayout(context)
        val inflater = LayoutInflater.from(context)

        for (i in 1..seatCount) {
            val seat = inflater.inflate(R.layout.seat_item, null) as ImageView

            // Burada istediğiniz özellikleri koltuklara ekleyebilirsiniz.
            seat.setImageResource(R.drawable.baseline_airline_seat_recline_normal_24)
            seat.setOnClickListener {
                // Koltuğa tıklandığında yapılacak işlemler
                // Örneğin, cinsiyet seçimi ve rengi değiştirme işlemleri burada olacak.
            }

            // GridLayout'a koltuğu ekle
            val params = GridLayout.LayoutParams()
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
            seatGridLayout.addView(seat, params)
        }
    }


    //cityFrom ve cityTo ile verileri yükleyen loadData() fonksiyonu
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

    //cardview'a tıklandığında çalışan onItemClick() fonksiyonu
    fun onItemClick(travelModel: Travel) {
        Toast.makeText(activity,"Clicked: ${travelModel.cityFrom} - ${travelModel.cityTo} - ${travelModel.id}",Toast.LENGTH_SHORT).show()
    }



    //Carda basınca açılan dialog
    fun showCustomDialog(content: String) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_dialog, null)
        builder.setView(dialogView)

        val contentTextView: TextView = dialogView.findViewById(R.id.contentTextView)
        contentTextView.text = content

        val okButton: Button = dialogView.findViewById(R.id.okButton)
        val dialog = builder.create()

        okButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showCustomDialog() {
        // Özel bir layout dosyasını kullanarak dialog oluştur
        val dialogView = layoutInflater.inflate(R.layout.dialog_reservation, null)

        // Dialog penceresini oluştur
        var dialog_reservation = Dialog(requireContext())
        dialog_reservation.setContentView(dialogView)
        dialog_reservation.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // Arkaplanı saydam yapmak için

        // Dialog içindeki bileşenlere erişim
        val btnSave = dialogView.findViewById<Button>(R.id.btnSave)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        // "Save" düğmesine tıklanınca
        btnSave.setOnClickListener {
            // İstenilen işlemleri burada gerçekleştirin
            Toast.makeText(requireContext(), "Save success", Toast.LENGTH_SHORT).show()

            // Dialog penceresini kapat
            dialog_reservation.dismiss()
        }

        // "Cancel" düğmesine tıklanınca
        btnCancel.setOnClickListener {
            // İstenilen işlemleri burada gerçekleştirin
            Toast.makeText(requireContext(), "It is cancelled", Toast.LENGTH_SHORT).show()

            // Dialog penceresini kapat
            dialog_reservation.dismiss()
        }

        // Dialog penceresini göster
        dialog_reservation.show()
    }


}
