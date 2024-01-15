package com.example.travelreservation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelreservation.model.Passenger
import android.view.LayoutInflater
import com.example.travelreservation.databinding.ItemPassengerBinding

class PassengerAdapter(
    private val passengerList: List<Passenger>
) : RecyclerView.Adapter<PassengerAdapter.PassengerViewHolder>() {

    inner class PassengerViewHolder(val binding: ItemPassengerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder {
        val binding =
            ItemPassengerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PassengerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return passengerList.size
    }

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {
        val currentPassenger = passengerList[position]

        // TODO: ViewHolder içindeki bileşenlere verileri bağla
        // Örneğin:
        // holder.binding.textViewName.text = currentPassenger.name
        // holder.binding.textViewSurname.text = currentPassenger.surname
        // vb.
    }


}
