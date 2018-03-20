package com.example.aj.emergencynotifier

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_set_number.*
import org.jetbrains.anko.startActivity

class SetNumberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_number)

        supportActionBar?.hide()

        confirm.setOnClickListener {

            if (phoneNumberEdit.text.toString().length > 0 ){
                var user = User(phoneNumberEdit.text.toString())
                var db = DataBaseHandler(this)
                var result = db.insertData(user)
                if(result.equals(true)){
                    Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()
                    startActivity<MainActivity>()
                    finish()
                }
                else{
                    Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show()
                } }

        }
    }
}
