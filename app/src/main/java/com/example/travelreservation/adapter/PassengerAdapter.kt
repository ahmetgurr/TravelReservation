package com.example.travelreservation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelreservation.databinding.ItemPassengerBinding
import com.example.travelreservation.model.Passenger

class PassengerAdapter(private val onDeleteClickListener: (Passenger) -> Unit) : RecyclerView.Adapter<PassengerAdapter.PassengerViewHolder>() {

    var passengerList: List<Passenger> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder {
        val binding = ItemPassengerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PassengerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return passengerList.size
    }

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {
        val currentItem = passengerList[position]
        holder.bind(currentItem)
    }

    fun submitList(passengers: List<Passenger>) {
        passengerList = passengers
        notifyDataSetChanged()
    }

    inner class PassengerViewHolder(private val binding: ItemPassengerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(passenger: Passenger) {
            binding.apply {
                textViewName.text = passenger.Name
                textViewSurname.text = passenger.Surname

                // ImageDelete simgesine tıklanma işlemi
                imageDelete.setOnClickListener {
                    onDeleteClickListener.invoke(passenger)
                }
            }
        }
    }
}

// Set data to views
/*
textSurname.text = passenger.Surname
textName.text = passenger.Name
textBirthDay.text = passenger.BirthDay
textPhone.text = passenger.Phone
textGender.text = passenger.Gender
 */