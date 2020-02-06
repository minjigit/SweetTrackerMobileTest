package com.example.sweettrackermobiletest.tracking

import com.example.sweettrackermobiletest.adapter.TrackingDetailAdapter
import com.example.sweettrackermobiletest.model.TrackingData
import com.example.sweettrackermobiletest.utils.RetrofitUrlConnect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Model(val requiredPresenter: Contract.RequiredPresenter) {

    var trackingData: TrackingData? = null

    fun getTrackingData(){
        val trackingDataService = RetrofitUrlConnect().trackingDataService
        trackingDataService?.getTrackingData()?.enqueue(object : Callback<TrackingData> {
            override fun onFailure(call: Call<TrackingData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<TrackingData>, response: Response<TrackingData>) {
                val result = response.body()
                if(result != null){
                    trackingData = result
                    requiredPresenter.setTrackingData(trackingData)
                }
            }
        })
    }

    fun getAdapter(){
        val adapter = TrackingDetailAdapter(trackingData?.trackingDetail!!)
        requiredPresenter.setAdapter(adapter)
    }
}