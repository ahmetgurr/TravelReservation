package com.example.travelreservation.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.travelreservation.R
import com.example.travelreservation.databinding.RowLayoutBinding
import com.example.travelreservation.model.Travel
import com.example.travelreservation.ui.fragment.PassengerInfoFragment
import com.example.travelreservation.ui.fragment.TravelListFragment
import com.example.travelreservation.ui.fragment.TravelListFragmentDirections
import java.util.zip.Inflater

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

            val travel = travelList[position]

            holder.itemView.setOnClickListener {
                listener.onItemClick(travel)
                //listener.showCustomDialog(travelList.toString())
            }

            holder.binding.btnConfirmAndContinue.setOnClickListener {
                val action = travel.id?.let { it1 ->
                    travel.cityFrom?.let { it2 ->
                        travel.cityTo?.let { it3 ->
                            TravelListFragmentDirections.actionTravelListFragmentToPassengerInfoFragment(
                                it1,
                                it2,
                                it3
                            )
                        }
                    }
                }
                if (action != null) {
                    Navigation.findNavController(it).navigate(action)
                }
            }


            //basınca genişleyen layout
            holder.binding.linearLayoutArrowDown.setOnClickListener {
                // Tıklanan öğenin durumunu kontrol et
                if (holder.binding.expandedLayout.visibility == View.GONE) {
                    // Genişletilecek bölüm gizliyse, görünür yap
                    holder.binding.expandedLayout.visibility = View.VISIBLE
                } else {
                    // Genişletilecek bölüm görünürse, gizle
                    holder.binding.expandedLayout.visibility = View.GONE
                }
            }

            holder.binding.textCityFrom.text = travelList.get(position).cityFrom
            holder.binding.textCityTo.text = travelList.get(position).cityTo
            holder.binding.textDistance.text = travelList.get(position).distance
            holder.binding.textHours.text = travelList.get(position).hours
            holder.binding.textPrice.text = travelList.get(position).price
        }


}
/*
   daha sonra bu fonksiyonu çağırabiliriz:
   fun updateTravelList(newTravelList: List<Travel>) {
           travelList.clear()
           travelList.addAll(newTravelList)
           notifyDataSetChanged()
       }

    */