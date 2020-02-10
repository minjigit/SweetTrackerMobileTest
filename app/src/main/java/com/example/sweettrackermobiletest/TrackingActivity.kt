package com.example.sweettrackermobiletest

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
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

            var status = "운송장 없음"
            var progressValue = 0

            when(this.parcelLevel){
                0 -> {
                    status = "집하"
                    progressValue = 66
                }
                1 -> {
                    status = "배송중"
                    progressValue = 132
                }
                2 -> {
                    status = "배달출발"
                    progressValue = 198
                }
                3 -> {
                    status = "배달완료"
                    progressValue = 268
                }
            }
            binding.status.text = status
            presenter?.getAdapter()

            val ani = ObjectAnimator.ofInt(binding.progressBar, "progress", progressValue)
            ani.duration = 3000
            ani.start()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){

        }
        return super.onOptionsItemSelected(item)
    }
}
