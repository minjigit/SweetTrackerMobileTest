package com.example.sweettrackermobiletest

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        trackingData?.let {
            binding.itemName.text = it.purchaseItemName
            binding.invoice.text = it.parcelInvoice
            binding.parcelCompany.text = it.parcelCompanyName
            binding.statusSub.text = "도착예정시간 : ${it.parcelDeliverTime}"
            binding.date.text = "등록일 : ${it.purchaseItemDate}"

            Glide
                .with(this@TrackingActivity)
                .load(it.purchaseItemImg)
                .into(binding.itemImg)

            var status = "운송장 없음"
            var progressValue = 0
            var pinValue = 0

            val pinHalfWidth = binding.pinImg.width / 2
            val progressWidth = binding.progressBar.width - pinHalfWidth
            val progressBlock = progressWidth / 3

            when(it.parcelLevel){
                1 -> {
                    status = "집하"

                    fadeAni(binding.step2Txt, 1000L)
                    fadeAni(binding.step3Txt, 1500L)
                    fadeAni(binding.step4Txt, 2000L)

                    changeOvalColorAni(binding.step1Oval, R.color.mainColor,0L)
                }
                2 -> {
                    status = "배송중"
                    progressValue = 33
                    pinValue = progressBlock

                    fadeAni(binding.step1Txt, 500L)
                    fadeAni(binding.step3Txt, 1500L)
                    fadeAni(binding.step4Txt, 2000L)

                    changeOvalColorAni(binding.step1Oval, R.color.subColor,0L)
                    changeOvalColorAni(binding.step2Oval, R.color.mainColor,1000L)
                }
                3 -> {
                    status = "배달출발"
                    progressValue = 66
                    pinValue = progressBlock * 2

                    fadeAni(binding.step1Txt, 500L)
                    fadeAni(binding.step2Txt, 1000L)
                    fadeAni(binding.step4Txt, 2000L)

                    changeOvalColorAni(binding.step1Oval, R.color.subColor,0L)
                    changeOvalColorAni(binding.step2Oval, R.color.subColor,1000L)
                    changeOvalColorAni(binding.step3Oval, R.color.mainColor,1500L)
                }
                4 -> {
                    status = "배달완료"
                    progressValue = 100
                    pinValue = progressBlock * 3

                    fadeAni(binding.step1Txt, 500L)
                    fadeAni(binding.step2Txt, 1000L)
                    fadeAni(binding.step3Txt, 1500L)

                    changeOvalColorAni(binding.step1Oval, R.color.subColor,0L)
                    changeOvalColorAni(binding.step2Oval, R.color.subColor,1000L)
                    changeOvalColorAni(binding.step3Oval, R.color.subColor,1500L)
                    changeOvalColorAni(binding.step4Oval, R.color.mainColor,2000L)
                }
            }
            binding.status.text = status
            presenter?.getAdapter()

            val progressAni = ObjectAnimator.ofInt(binding.progressBar, "progress", progressValue)
            progressAni.duration = 3000L
            progressAni.start()

            val pinMoveAni = ObjectAnimator.ofFloat(binding.pinImg,"translationX", pinValue.toFloat())
            pinMoveAni.duration = 3000L
            pinMoveAni.start()
        }
    }

    private fun convertDpToPixels(value: Int): Float{
        return value * resources.displayMetrics.density
    }

    private fun changeOvalColorAni(view: View, resource: Int, delay: Long){
        val oval = view.background as? GradientDrawable
        val color = ContextCompat.getColor(this@TrackingActivity, resource)
        val pixel = convertDpToPixels(4).toInt()

        ValueAnimator.ofFloat(0f,1f).apply {
            startDelay = delay
            addUpdateListener {
                oval?.setStroke(pixel, color)
            }
            start()
        }
    }

    private fun fadeAni(view: View, delay: Long){
        ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f).apply {
            startDelay = delay
            duration = 300L
            addListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                    view.visibility = View.VISIBLE
                }
            })
            start()
        }
    }

    override fun setAdapter(trackingAdapter: TrackingDetailAdapter) {
        val manager = LinearLayoutManager(this)

        binding.detailList.apply {
            adapter = trackingAdapter
            layoutManager = manager as RecyclerView.LayoutManager?
            isNestedScrollingEnabled = false
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
