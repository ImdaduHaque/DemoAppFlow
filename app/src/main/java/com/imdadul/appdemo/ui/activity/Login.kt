package com.imdadul.appdemo.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.imdadul.appdemo.R

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)

        val btn_login = findViewById<TextView>(R.id.tv_login)

        btn_login.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)
            finish()
        })
    }
}
