package com.example.travelreservation.adapter
/*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelreservation.R
import com.example.travelreservation.model.Ticket

class TicketsAdapter(
    private val context: Context,
    private val ticketList: List<Ticket>
) : RecyclerView.Adapter<TicketsAdapter.TicketViewHolder>() {

    inner class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityFrom: TextView = itemView.findViewById(R.id.text_cityFrom)
        val cityTo: TextView = itemView.findViewById(R.id.textCityTo)
        val passengerName: TextView = itemView.findViewById(R.id.textViewName)
        val ticketId: TextView = itemView.findViewById(R.id.textViewId)
        val seatNumber: TextView = itemView.findViewById(R.id.textViewSeatNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return TicketViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val currentTicket = ticketList[position]

        holder.cityFrom.text = currentTicket.cityFrom
        holder.cityTo.text = currentTicket.cityTo
        holder.passengerName.text = currentTicket.userName
        holder.ticketId.text = currentTicket.id
        holder.seatNumber.text = currentTicket.seatNumber.toString()

    }

    override fun getItemCount(): Int {
        return ticketList.size
    }
}
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelreservation.R
import com.example.travelreservation.model.Ticket

class TicketsAdapter(
    private val tickets: List<Ticket>
) : RecyclerView.Adapter<TicketsAdapter.TicketViewHolder>() {

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val seatNumberTextView: TextView = itemView.findViewById(R.id.textViewSeatNumber)
        val cityFromTextView: TextView = itemView.findViewById(R.id.text_cityFrom)
        val cityToTextView: TextView = itemView.findViewById(R.id.textCityTo)
        val userNameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val ticketIdTextView: TextView = itemView.findViewById(R.id.textViewId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.seatNumberTextView.text = "Seat No:${ticket.seatNumber}"
        holder.cityFromTextView.text = "${ticket.cityFrom}"
        holder.cityToTextView.text = "${ticket.cityTo}"
        holder.userNameTextView.text = "${ticket.userName}"
        holder.ticketIdTextView.text = "Travel ID: ${ticket.id}"
    }

    override fun getItemCount(): Int {
        return tickets.size
    }
}


