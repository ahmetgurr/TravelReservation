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
    private var selectedPassengerId: String? = null

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
        // travel.id'yi logda yazdır
        Log.d("PassengerInfoFragment", "Selected Item ID: $selectedItemId")

        val textCityFrom: TextView = view.findViewById(R.id.text_cityFrom)
        val textCityTo: TextView = view.findViewById(R.id.textCityTo)

        textCityFrom.text = selectedCityFrom
        textCityTo.text = selectedCityTo

        // Add Passenger Button
        binding.btnAddPassengers.setOnClickListener {
            Navigation.nextPage(R.id.action_passengerInfoFragment_to_addPassengerFragment, it)
            Toast.makeText(context, "Add Passenger", Toast.LENGTH_SHORT).show()
        }

        binding.btnContinue.setOnClickListener {
            val selectedCityFrom = binding.textCityFrom.text.toString()
            val selectedCityTo = binding.textCityTo.text.toString()

            val action = PassengerInfoFragmentDirections
                .actionPassengerInfoFragmentToChooseSeatFragment(
                    selectedCityFrom,
                    selectedCityTo,
                    selectedPassengerId ?: ""
                )
            Navigation.findNavController(it).navigate(action)
            Toast.makeText(context, "Ticket List", Toast.LENGTH_SHORT).show()
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
        loadPassengers()
        // CardView'da seçili yolcuyu seçtikten sonra seçili şehiri göstermek için
        passengerAdapter.setSelectedCities(selectedCityFrom, selectedCityTo)
    }
    private fun loadPassengers() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val passengersCollection = FirebaseFirestore.getInstance()
            .collection("Users")
            .document(userId!!)
            .collection("Passengers")

        passengersCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("PassengerInfoFragment", "Error getting passengers", error)
                return@addSnapshotListener
            }
            val passengers = mutableListOf<Passenger>()
            snapshot?.documents?.forEach { document ->
                val passenger = document.toObject(Passenger::class.java)
                if (passenger != null) {
                    passengers.add(passenger)
                    // Seçili yolcunun ID'sini burada kontrol edebilir ve atayabilirsiniz.
                    if (passenger.isSelected) {
                        selectedPassengerId = passenger.id
                    }
                }
            }

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
