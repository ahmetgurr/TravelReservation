package com.example.travelreservation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelreservation.R
import com.example.travelreservation.databinding.RowLayoutBinding
import com.example.travelreservation.model.Travel
import com.example.travelreservation.ui.fragment.TravelListFragment

class TravelRecyclerAdapter(
    var travelList: ArrayList<Travel>,
    var listener: TravelListFragment
) : RecyclerView.Adapter<TravelRecyclerAdapter.RowHolder>() {

        class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
            val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return RowHolder(binding)
        }

        override fun getItemCount(): Int {
            return travelList.count()
        }

        override fun onBindViewHolder(holder: RowHolder, position: Int) {

            holder.binding.textCityFrom.text = travelList.get(position).cityFrom
            holder.binding.textCityTo.text = travelList.get(position).cityTo
            holder.binding.textDistance.text = travelList.get(position).distance

        }
}
