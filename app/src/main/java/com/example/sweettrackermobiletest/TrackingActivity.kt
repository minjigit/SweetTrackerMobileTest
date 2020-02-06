package com.example.sweettrackermobiletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.sweettrackermobiletest.adapter.TrackingDetailAdapter
import com.example.sweettrackermobiletest.databinding.ActivityTrackingBinding
import com.example.sweettrackermobiletest.model.TrackingData
import com.example.sweettrackermobiletest.tracking.Contract
import com.example.sweettrackermobiletest.tracking.Presenter

class TrackingActivity : AppCompatActivity(), Contract.View {
    lateinit var binding: ActivityTrackingBinding
    private var presenter: Contract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tracking)
        presenter = Presenter(this)
        presenter?.getTrackingData()
    }

    override fun setTrackingData(trackingData: TrackingData?) {
        trackingData?.apply {
            binding.itemName.text = this.purchaseItemName
            binding.invoice.text = this.parcelInvoice
            binding.parcelCompany.text = this.parcelCompanyName
            binding.statusSub.text = "도착예정시간 : ${this.parcelDeliverTime}"
            binding.dateTxt.text = "등록일 : ${this.purchaseItemDate}"

            Glide
                .with(this@TrackingActivity)
                .load(this.purchaseItemImg)
                .into(binding.itemImg)
        }
    }

    override fun setAdapter(adapter: TrackingDetailAdapter) {
        binding.detailList.adapter = adapter
    }
}
