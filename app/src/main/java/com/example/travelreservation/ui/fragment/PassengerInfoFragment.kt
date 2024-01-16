package com.example.travelreservation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelreservation.R
import com.example.travelreservation.adapter.PassengerAdapter
import com.example.travelreservation.databinding.FragmentPassengerInfoBinding
import com.example.travelreservation.model.Passenger
import com.example.travelreservation.util.nextPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PassengerInfoFragment : Fragment() {

    private lateinit var binding: FragmentPassengerInfoBinding
    private lateinit var passengerAdapter: PassengerAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPassengerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // TravelListFragment'den taşınan verileri al
        val selectedItemId = arguments?.getString("selectedItemId")
        val selectedCityFrom = arguments?.getString("selectedCityFrom")
        val selectedCityTo = arguments?.getString("selectedCityTo")

        // TextView'ları bul
        val textCityFrom: TextView = view.findViewById(R.id.text_cityFrom)
        val textCityTo: TextView = view.findViewById(R.id.textCityTo)

        // TextView'lara verileri ekle
        textCityFrom.text = selectedCityFrom
        textCityTo.text = selectedCityTo

        // Add Passenger Button
        binding.btnAddPassengers.setOnClickListener{
            Navigation.nextPage(R.id.action_passengerInfoFragment_to_addPassengerFragment,it)
            Toast.makeText(context, "Add Passenger", Toast.LENGTH_SHORT).show()
        }


        // RecyclerView ve Adapter'ı oluşturun
        recyclerView = binding.recyclerView
        passengerAdapter = PassengerAdapter { passenger ->
            deletePassenger(passenger)
        }

        // LayoutManager ayarlayın (örneğin, LinearLayoutManager kullanabilirsiniz)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Adapter'ı RecyclerView'e bağlayın
        recyclerView.adapter = passengerAdapter

        // Firestore'dan yolcu verilerini alın ve Adapter'a ekleyin
        loadPassengers(selectedItemId)

    }

    private fun loadPassengers(selectedItemId: String?) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val passengersCollection = FirebaseFirestore.getInstance()
            .collection("Users")
            .document(userId!!)
            .collection("Passengers")

        passengersCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // Hata durumu
                Log.e("PassengerInfoFragment", "Error getting passengers", error)
                return@addSnapshotListener
            }

            // Verileri alma ve Adapter'a ekleme işlemleri
            val passengers = mutableListOf<Passenger>()
            snapshot?.documents?.forEach { document ->
                val passenger = document.toObject(Passenger::class.java)
                if (passenger != null) {
                    passengers.add(passenger)
                }
            }

            // RecyclerView için Adapter'a verileri ata
            passengerAdapter.submitList(passengers)
        }
    }

    private fun deletePassenger(passenger: Passenger) {
        Log.d("DeletePassenger", "Delete passenger function started.")
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val passengersCollection = FirebaseFirestore.getInstance()
            .collection("Users")
            .document(userId!!)
            .collection("Passengers")

        passenger.id?.let { passengerId ->
            Log.d("DeletePassenger", "Passenger ID: $passengerId")
            passengersCollection.document(passengerId).delete()
                .addOnSuccessListener {
                    Log.d("DeletePassenger", "Successfully deleted passenger.")
                    // Firestore'dan başarıyla silindi

                    // Silinen yolcuyu güncelleyerek RecyclerView'ı güncelleyin
                    val updatedPassengers = passengerAdapter.passengerList.toMutableList()
                    updatedPassengers.remove(passenger)
                    passengerAdapter.submitList(updatedPassengers)

                    Toast.makeText(requireContext(), "Yolcu başarıyla silindi", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Firestore'dan silme işlemi başarısız oldu
                    Log.e("DeletePassenger", "Error deleting passenger", it)
                    Toast.makeText(requireContext(), "Yolcu silinemedi", Toast.LENGTH_SHORT).show()
                }
        }
    }


}