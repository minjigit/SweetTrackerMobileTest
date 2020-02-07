package com.example.sweettrackermobiletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
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

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setTrackingData(trackingData: TrackingData?) {
        trackingData?.apply {
            binding.itemName.text = this.purchaseItemName
            binding.invoice.text = this.parcelInvoice
            binding.parcelCompany.text = this.parcelCompanyName
            binding.statusSub.text = "도착예정시간 : ${this.parcelDeliverTime}"
            binding.date.text = "등록일 : ${this.purchaseItemDate}"

            Glide
                .with(this@TrackingActivity)
                .load(this.purchaseItemImg)
                .into(binding.itemImg)

            val status = when(this.parcelLevel){
                0 -> "집하"
                1 -> "배송중"
                2 -> "배달출발"
                3 -> "배달완료"
                else -> "운송장 없음"
            }

            binding.status.text = status
            presenter?.getAdapter()
        }
    }

    override fun setAdapter(adapter: TrackingDetailAdapter) {
        val layoutManager = LinearLayoutManager(this)

        binding.detailList.let {
            it.adapter = adapter
            it.layoutManager = layoutManager
            it.isNestedScrollingEnabled = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){

        }
        return super.onOptionsItemSelected(item)
    }
}
