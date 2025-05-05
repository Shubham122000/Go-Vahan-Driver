package com.gvpartner.com

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivitySignup2Binding
import com.gvpartner.com.ui.common.LoginActivity

class SignUp2 : BaseActivity() {
    private lateinit var binding: ActivitySignup2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_signup2)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup2)


        binding.btnSubmit.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()

        })

    }
}