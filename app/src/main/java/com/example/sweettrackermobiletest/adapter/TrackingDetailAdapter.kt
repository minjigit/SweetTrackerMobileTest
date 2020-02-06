package com.example.sweettrackermobiletest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sweettrackermobiletest.R
import com.example.sweettrackermobiletest.model.TrackingDetailData

class TrackingDetailAdapter(private val data: ArrayList<TrackingDetailData>) : RecyclerView.Adapter<TrackingDetailAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tracking_detail_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

    }
}