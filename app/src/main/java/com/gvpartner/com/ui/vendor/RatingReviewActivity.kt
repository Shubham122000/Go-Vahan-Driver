package com.gvpartner.com.ui.vendor

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvpartner.com.R
import com.gvpartner.com.adapter.ReviewAdapter
import com.gvpartner.com.databinding.ActivityRatingReviewBinding
import java.util.*
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.model.RatingResponseData
import com.gvpartner.com.viewmodel.RatingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class RatingReviewActivity : BaseActivity() {
    private lateinit var binding : ActivityRatingReviewBinding
    private val viewModel : RatingViewModel by viewModels()
    var List : ArrayList<RatingResponseData> = ArrayList()
    lateinit var adapter : ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_rating_review)


        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.RatingAPI(
            "Bearer "+ userPref.getToken().toString(),
        )
        viewModel.RatingResponse.observe(this) {
            if (it?.status == 1) {
                List.clear()
                List.addAll(it.data)
                binding.etFeedback.text = it.review.toString()+" Reviews"
                binding.rvReview.layoutManager = LinearLayoutManager(this)
                adapter = ReviewAdapter(this, List)
                binding.rvReview.adapter =adapter

////                userPref.setUserId(it!!.data!!.Id.toString())
//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
//                finish()
            } else {
//                toast(.message)
            }
        }

    }
}