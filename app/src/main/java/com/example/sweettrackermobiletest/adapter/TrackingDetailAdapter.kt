package com.example.sweettrackermobiletest.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sweettrackermobiletest.databinding.TrackingDetailItemBinding
import com.example.sweettrackermobiletest.model.TrackingDetailData

class TrackingDetailAdapter(private val data: List<TrackingDetailData>) : RecyclerView.Adapter<TrackingDetailAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TrackingDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }


    class ViewHolder(private val binding: TrackingDetailItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: TrackingDetailData){
            binding.status.text = item.status
            binding.location.text = item.where

            val dateArr = item.time?.split(" ")
            binding.date.text = dateArr?.get(0) ?: " "
            binding.time.text = dateArr?.get(1) ?: " "
        }
    }
}