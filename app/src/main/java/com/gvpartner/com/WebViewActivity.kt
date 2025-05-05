package com.gvpartner.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.gvpartner.com.R

class WebViewActivity : AppCompatActivity() {


    var phonepay:String=""
    var transaction:String=""

    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)


        phonepay=intent.getStringExtra("phonepay").toString()

        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(phonepay)
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)


    }


    override fun onBackPressed() {
        if (webView.canGoBack())
            webView.goBack()



        super.onBackPressed()
    }

}