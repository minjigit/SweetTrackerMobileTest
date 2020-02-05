package com.example.sweettrackermobiletest

import android.util.Log
import com.example.sweettrackermobiletest.model.TrackingData
import com.example.sweettrackermobiletest.utils.RetrofitUrlConnect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParcelTrackingModel {

    fun getTrackingData(){
        //레트로핏 받아오는 부분 수정 필요

        val trackingDataService = RetrofitUrlConnect().trackingDataService
        trackingDataService?.getTrackingData()?.enqueue(object : Callback<TrackingData>{
            override fun onFailure(call: Call<TrackingData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<TrackingData>, response: Response<TrackingData>) {
                val result = response.body()
                if(result != null){
                    Log.e("테스트", result.toString())
                }
            }
        })
    }
}