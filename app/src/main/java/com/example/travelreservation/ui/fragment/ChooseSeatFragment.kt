package com.example.travelreservation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.travelreservation.R
import com.example.travelreservation.databinding.FragmentChooseSeatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChooseSeatFragment : Fragment() {
    private lateinit var binding: FragmentChooseSeatBinding
    private var selectedSeatNumber: Int? = null
    private val reservedSeats = mutableListOf<Int>()
    private var selectedItemId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseSeatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Diğer verileri al
        val textViewName = arguments?.getString("textViewName")
        val textViewSurname = arguments?.getString("textViewSurname")
        val selectedCityFrom = arguments?.getString("selectedCityFrom")
        val selectedCityTo = arguments?.getString("selectedCityTo")

        // selectedItemId'yi al
        selectedItemId = arguments?.getString("selectedItemId")

        Log.d("ChooseSeatFragment selectedItemId", "Selected Item ID: $selectedItemId")

        val textName: TextView = view.findViewById(R.id.textViewName)
        val textSurname: TextView = view.findViewById(R.id.textViewSurname)
        val textSelectedCityFrom: TextView = view.findViewById(R.id.text_cityFrom)
        val textSelectedCityTo: TextView = view.findViewById(R.id.textCityTo)

        textName.text = textViewName
        textSurname.text = textViewSurname
        textSelectedCityFrom.text = selectedCityFrom
        textSelectedCityTo.text = selectedCityTo

        // Koltukları oluştur
        createSeats()

        // Rezervasyon yap butonuna tıklanma işlemi
        binding.btnMakeReservation.setOnClickListener {
            if (selectedSeatNumber != null) {
                makeReservation()
                val action = ChooseSeatFragmentDirections.actionChooseSeatFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            } else {
                Toast.makeText(requireContext(), "Please select a seat", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createSeats() {
        val gridLayout = binding.gridLayoutSeats
        gridLayout.rowCount = 5
        gridLayout.columnCount = 5

        for (row in 0 until gridLayout.rowCount) {
            for (column in 0 until gridLayout.columnCount) {
                val seatNumber = row * gridLayout.columnCount + column + 1
                val cardView = createSeatCard(seatNumber)
                val params = GridLayout.LayoutParams()
                params.rowSpec = GridLayout.spec(row)
                params.columnSpec = GridLayout.spec(column)
                cardView.layoutParams = params
                gridLayout.addView(cardView)

                // Firebase'den rezervasyon bilgilerini al
                getReservationInfo(seatNumber)
                // Koltukların boyutunu ayarla
                params.width = 175
                params.height = 150
            }
        }
    }

    private fun createSeatCard(seatNumber: Int): CardView {
        val cardView = CardView(requireContext())
        cardView.radius = 8f
        cardView.cardElevation = 4f
        cardView.useCompatPadding = true
        cardView.setContentPadding(20, 20, 20, 20)

        val textView = TextView(requireContext())
        textView.text = seatNumber.toString()
        textView.textSize = 20f
        textView.gravity = Gravity.CENTER

        cardView.addView(textView)

        cardView.setOnClickListener {
            handleSeatSelection(seatNumber)
        }
        return cardView
    }

    private fun handleSeatSelection(seatNumber: Int) {
        selectedSeatNumber?.let {
            changeSeatColor(binding.gridLayoutSeats, it, false)
        }

        val selectedSeatText = "Seçili Koltuk: $seatNumber"
        binding.textSelectedSeat.text = selectedSeatText

        selectedSeatNumber = seatNumber

        changeSeatColor(binding.gridLayoutSeats, seatNumber, true)
    }

    private fun changeSeatColor(gridLayout: GridLayout, seatNumber: Int, isLastClicked: Boolean) {
        val cardView = gridLayout.getChildAt(seatNumber - 1) as CardView

        if (isLastClicked || seatNumber in reservedSeats) {
            // Eğer en son tıklanan koltuksa veya Firebase'de kayıtlıysa
            cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorBooked))
        } else {
            // Hiçbir işlem yapılmamışsa (Firebase'de kayıtlı değilse ve en son tıklanan değilse)
            cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorAvailable))
        }
    }

    private fun makeReservation() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val firestore = FirebaseFirestore.getInstance()

        // selectedItemId'ye göre "Users" koleksiyonunun altında "Reservation" koleksiyonu oluştur
        val reservationCollection = firestore.collection("Users").document(userId!!)
            .collection("Reservation").document(selectedItemId!!)
            .collection("Seats")

        val reservationData = hashMapOf(
            "seatNumber" to selectedSeatNumber,
            "cityFrom" to binding.textCityFrom.text.toString(),
            "cityTo" to binding.textCityTo.text.toString(),
            "userName" to "${binding.textViewName.text} ${binding.textViewSurname.text}",
            "selectedItemId" to selectedItemId  // selectedItemId'yi ekle
        )

        reservationCollection.add(reservationData)
            .addOnSuccessListener { documentReference ->
                val reservationId = documentReference.id
                reservationCollection.document(reservationId).update("id", reservationId)
                selectedSeatNumber?.let { changeSeatColor(binding.gridLayoutSeats, it, true) }
                Toast.makeText(requireContext(), "Reservation made successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Reservation could not be made", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getReservationInfo(seatNumber: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val firestore = FirebaseFirestore.getInstance()

        // selectedItemId'ye göre "Users" koleksiyonunun altında "Reservation" koleksiyonunu al
        val reservationCollection = firestore.collection("Users").document(userId!!)
            .collection("Reservation").document(selectedItemId!!)
            .collection("Seats")

        reservationCollection
            .whereEqualTo("seatNumber", seatNumber)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Eğer rezervasyon yapılmışsa, reservedSeats listesine ekle
                    val reservedSeat = document.getLong("seatNumber")?.toInt()
                    reservedSeat?.let { reservedSeats.add(it) }
                }
                // Koltuk rengini güncelle
                changeSeatColor(binding.gridLayoutSeats, seatNumber, reservedSeats.contains(seatNumber))
            }
            .addOnFailureListener { exception ->
                Log.w("ChooseSeatFragment", "Error getting reservation info", exception)
            }
    }
}

