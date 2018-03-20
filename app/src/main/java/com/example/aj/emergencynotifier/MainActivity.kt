package com.example.aj.emergencynotifier

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.test.mock.MockPackageManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.ankit.gpslibrary.MyTracker
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivityForResult


class MainActivity : AppCompatActivity() {
    val CAMERA_REQUEST_CODE=0
    val REQUEST_CODE_PERMISSION = 2
    val mPermission : String = Manifest.permission.ACCESS_FINE_LOCATION
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        fire.setOnClickListener {
            checkLocation()
        }
        nineOneOne.setOnClickListener {
            checkLocation()
        }
        police.setOnClickListener {
            checkLocation()
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CAMERA_REQUEST_CODE ->{
                if(resultCode == Activity.RESULT_OK && data != null){
                    //imageView.setImageBitmap(data.extras.get("data") as Bitmap)
                    getLocation()

                }
            }
            else -> {
                Toast.makeText(this,"Unrecognized request code",Toast.LENGTH_SHORT)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_PERMISSION ->{
                if( grantResults.isNotEmpty() && grantResults[0].equals(PackageManager.PERMISSION_GRANTED)){
                    openCamera()
                }
                else{
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show()
                }
            }

        }

    }
    fun openCamera(){
        val cameraIntent : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(cameraIntent.resolveActivity(packageManager)!=null){
            startActivityForResult(cameraIntent,CAMERA_REQUEST_CODE)
        }
    }
    fun checkLocation() {
       try {
            if(ActivityCompat.checkSelfPermission(this,mPermission) != MockPackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(mPermission, Manifest.permission.READ_PHONE_STATE),REQUEST_CODE_PERMISSION)
            }
           else{
                openCamera()
            }


       }catch (e : Exception){
            println("===============ERROR=====================")
            println("$e")
       }
    }

    fun getLocation(){
        val  tracker = MyTracker(this)
        alert("latitude:" + tracker.latitude + " longitude:" + tracker.longitude  + " macAddress:" + tracker.macAddress){  }.show()
    }



}
