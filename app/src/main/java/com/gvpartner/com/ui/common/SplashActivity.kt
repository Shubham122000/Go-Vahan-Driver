package com.gvpartner.com.ui.common

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.prefers.UserPref
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivitySplashBinding
import com.gvpartner.com.ui.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private  val splashTimeout : Long = 2000 //3sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        Handler().postDelayed({

           // val intent = Intent(this, LoginActivity::class.java)

            val userPref = UserPref(this)
            if(userPref.isLogin){
                if (userPref.getdriver_license()==null){
                    val intent = Intent(this, ChooseYourLanguageActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }else{
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }


            }else {
                   val intent = Intent(this, ChooseYourLanguageActivity::class.java)
            startActivity(intent)
            finish()
            }
        },splashTimeout)

    }
}