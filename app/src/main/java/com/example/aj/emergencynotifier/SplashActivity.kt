package com.example.aj.emergencynotifier

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler().postDelayed({
//            startActivity<SetNumberActivity>()
//            finish()
            var db = DataBaseHandler(this)
            var data = db.readData()
            if(data.size <= 0){
                startActivity<SetNumberActivity>()
                finish()
            }
            else{
                startActivity<MainActivity>()
                finish()
            }


        },2000)
    }
}
