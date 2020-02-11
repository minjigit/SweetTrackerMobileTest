package com.example.sweettrackermobiletest

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
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
import io.reactivex.internal.operators.flowable.FlowableRangeLong

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
            var pinValue = 0

            when(this.parcelLevel){
                0 -> {
                    status = "집하"

                    fadeAni(binding.step2Txt, 1000)
                    fadeAni(binding.step3Txt, 1500)
                    fadeAni(binding.step4Txt, 2000)

                    changeOvalColorAni(binding.step1Oval, R.color.mainColor,0)
                }
                1 -> {
                    status = "배송중"
                    progressValue = 33
                    pinValue = 84

                    fadeAni(binding.step1Txt, 500)
                    fadeAni(binding.step3Txt, 1500)
                    fadeAni(binding.step4Txt, 2000)

                    changeOvalColorAni(binding.step1Oval, R.color.subColor,0)
                    changeOvalColorAni(binding.step2Oval, R.color.mainColor,1000)
                }
                2 -> {
                    status = "배달출발"
                    progressValue = 66
                    pinValue = 168

                    fadeAni(binding.step1Txt, 500)
                    fadeAni(binding.step2Txt, 1000)
                    fadeAni(binding.step4Txt, 2000)

                    changeOvalColorAni(binding.step1Oval, R.color.subColor,0)
                    changeOvalColorAni(binding.step2Oval, R.color.subColor,1000)
                    changeOvalColorAni(binding.step3Oval, R.color.mainColor,1500)
                }
                3 -> {
                    status = "배달완료"
                    progressValue = 100
                    pinValue = 252

                    fadeAni(binding.step1Txt, 500)
                    fadeAni(binding.step2Txt, 1000)
                    fadeAni(binding.step3Txt, 1500)

                    changeOvalColorAni(binding.step1Oval, R.color.subColor,0)
                    changeOvalColorAni(binding.step2Oval, R.color.subColor,1000)
                    changeOvalColorAni(binding.step3Oval, R.color.subColor,1500)
                    changeOvalColorAni(binding.step4Oval, R.color.mainColor,2000)
                }
            }
            binding.status.text = status
            presenter?.getAdapter()

            val progressAni = ObjectAnimator.ofInt(binding.progressBar, "progress", progressValue)
            progressAni.duration = 3000
            progressAni.start()

            val pixel = convertDpToPixels(pinValue)
            val pinMoveAni = ObjectAnimator.ofFloat(binding.pinImg,"translationX", pixel)
            pinMoveAni.duration = 3000
            pinMoveAni.start()

            val statusFadeAni = ObjectAnimator.ofFloat(binding.step1Txt, "alpha", 0.0f, 1.0f)
            statusFadeAni.duration = 3000
            statusFadeAni.start()
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
            duration = 300
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

    override fun setAdapter(adapter: TrackingDetailAdapter) {
        val layoutManager = LinearLayoutManager(this)

        binding.detailList.let {
            it.adapter = adapter
            it.layoutManager = layoutManager as RecyclerView.LayoutManager?
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
