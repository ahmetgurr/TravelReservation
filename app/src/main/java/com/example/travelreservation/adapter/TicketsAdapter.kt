package com.example.travelreservation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelreservation.databinding.ItemTicketBinding
import com.example.travelreservation.model.Ticket

class TicketsAdapter(
    private val tickets: List<Ticket>,
    private val onDeleteClickListener: OnDeleteClickListener
) : RecyclerView.Adapter<TicketsAdapter.TicketViewHolder>() {

    interface OnDeleteClickListener {
        fun onDeleteClick(ticket: Ticket)
    }

    inner class TicketViewHolder(private val binding: ItemTicketBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ticket: Ticket) {
            binding.apply {
                textViewSeatNumber.text = "Seat No:${ticket.seatNumber}"
                textCityFrom.text = "${ticket.cityFrom}"
                textCityTo.text = "${ticket.cityTo}"
                textViewName.text = "${ticket.userName}"
                textViewId.text = "Travel ID: ${ticket.id}"

                buttonDelete.setOnClickListener {
                    onDeleteClickListener.onDeleteClick(ticket)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.bind(ticket)
    }
}




