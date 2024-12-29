package com.govahanpartner.com.ui.driver

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.SignUp2
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivitySignupBinding

class SignupActivity : BaseActivity() {

    private lateinit var binding : ActivitySignupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_signup)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        binding.btnSubmit.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SignUp2::class.java)
            startActivity(intent)

        })




        /*<LinearLayout
                android:id="@+id/container"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"/>
        </LinearLayout>*/

        // add more
        /*var button=findViewById<AppCompatButton>(R.id.btn_addmore_vehicleregis)
        button.setOnClickListener{
            var et=EditText(this)
            et.setText("umesh")
            var container=findViewById<LinearLayout>(R.id.container)
            container.addView(et)

        }*/


    }
}