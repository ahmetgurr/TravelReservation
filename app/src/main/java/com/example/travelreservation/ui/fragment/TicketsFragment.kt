package com.example.travelreservation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelreservation.R
import com.example.travelreservation.adapter.TicketsAdapter
import com.example.travelreservation.databinding.FragmentTicketsBinding
import com.example.travelreservation.model.Ticket
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TicketsFragment : Fragment() {
    private lateinit var binding: FragmentTicketsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var ticketsAdapter: TicketsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTicketsBinding.inflate(inflater, container, false)
        val context = requireContext() // Bu satırı ekleyin
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val firestore = FirebaseFirestore.getInstance()
        val reservationCollection = firestore.collection("Users").document(userId!!)
            .collection("Reservation")

        // Firebase'den rezervasyon verilerini al
        reservationCollection.addSnapshotListener { snapshots, exception ->
            if (exception != null) {
                // Hata durumu
                return@addSnapshotListener
            }

            val ticketList = mutableListOf<Ticket>()

            for (document in snapshots!!) {
                // Firestore'dan gelen verileri al
                val seatNumber = document.getLong("seatNumber")?.toInt()
                val cityFrom = document.getString("cityFrom")
                val cityTo = document.getString("cityTo")
                val userName = document.getString("userName")

                // Ticket objesini oluştur
                val ticket = Ticket(seatNumber, cityFrom, cityTo, userName, document.id)
                ticketList.add(ticket)
            }

            // RecyclerView için adapter ve layout manager'ı ayarla
            ticketsAdapter = TicketsAdapter(ticketList)

            // Fragment'ın attach olduğu durumu kontrol et
            if (isAdded) {
                binding.recyclerViewTickets.adapter = ticketsAdapter
                binding.recyclerViewTickets.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}