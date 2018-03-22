package com.example.aj.emergencynotifier

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_information.*
import org.jetbrains.anko.alert

class InformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        title = "Update Information"

        var db = DataBaseHandler(this)
        var data = db.readData()


        updateNumberEdit.setText(data.get(0).number.toString())
        updateNameEdit.setText(data.get(0).name.toString())



        var oldNumber = updateNumberEdit.text.toString()
        var oldName = updateNameEdit.text.toString()

        updateButton.setOnClickListener {

            if(oldNumber.equals(updateNumberEdit.text.toString()) && oldName.equals(updateNameEdit.text.toString())){
                Toast.makeText(this,"You cant update with the same value", Toast.LENGTH_LONG).show()
            }
            else{
                db.update(updateNumberEdit.text.toString(),updateNameEdit.text.toString())
                oldNumber = updateNumberEdit.text.toString()
                Toast.makeText(this,"User information update successfully", Toast.LENGTH_LONG).show()
            }

        }

    }
}
