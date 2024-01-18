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
import com.example.travelreservation.R
import com.example.travelreservation.databinding.FragmentChooseSeatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChooseSeatFragment : Fragment() {
    private lateinit var binding: FragmentChooseSeatBinding
    private var selectedSeatNumber: Int? = null
    private val reservedSeats = mutableListOf<Int>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseSeatBinding.inflate(inflater, container, false)
        return binding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewName = arguments?.getString("textViewName")
        val textViewSurname = arguments?.getString("textViewSurname")
        val selectedCityFrom = arguments?.getString("selectedCityFrom")
        val selectedCityTo = arguments?.getString("selectedCityTo")

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
            makeReservation()
        }


    }
    private fun createSeats() {
        val gridLayout = binding.gridLayoutSeats

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
            }
        }
    }


    private fun createSeatCard(seatNumber: Int): CardView {
        val cardView = CardView(requireContext())
        cardView.radius = 8f
        cardView.cardElevation = 4f
        cardView.useCompatPadding = true
        cardView.setContentPadding(16, 16, 16, 16)

        val textView = TextView(requireContext())
        textView.text = seatNumber.toString()
        textView.textSize = 18f
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
        val reservationCollection =
            firestore.collection("Users").document(userId!!).collection("Reservation")

        val reservationData = hashMapOf(
            "seatNumber" to selectedSeatNumber,
            "cityFrom" to "SehirFrom",
            "cityTo" to "SehirTo",
            "userName" to "Kullanıcı Adı",
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
        val reservationCollection = firestore.collection("Users").document(userId!!)
            .collection("Reservation")

        reservationCollection
            .whereEqualTo("seatNumber", seatNumber)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Belirli koltuğun rezervasyon bilgisi bulundu
                    // Eğer rezervasyon yapılmışsa, reservedSeats listesine ekleyebilirsiniz
                    val reservedSeat = document.getLong("seatNumber")?.toInt()
                    reservedSeat?.let { reservedSeats.add(it) }
                }
                // Koltuk rengini güncelle, işte burada çağırabilirsiniz
                changeSeatColor(binding.gridLayoutSeats, seatNumber, reservedSeats.contains(seatNumber))
            }
            .addOnFailureListener { exception ->
                // Hata durumunda buraya düşer
                Log.w("ChooseSeatFragment", "Error getting reservation info", exception)
            }
    }


}