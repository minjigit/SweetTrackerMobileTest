package com.example.sweettrackermobiletest.tracking

import com.example.sweettrackermobiletest.adapter.TrackingDetailAdapter
import com.example.sweettrackermobiletest.model.TrackingData

interface Contract {

    interface View {
        fun setTrackingData(trackingData: TrackingData?)
        fun setAdapter(trackingAdapter: TrackingDetailAdapter)
    }

    interface Presenter {
        fun getTrackingData()
        fun getAdapter()
    }

    interface RequiredPresenter {
        fun setTrackingData(trackingData: TrackingData?)
        fun setAdapter(trackingAdapter: TrackingDetailAdapter)
    }
}