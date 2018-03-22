package com.example.aj.emergencynotifier

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.test.mock.MockPackageManager
import android.widget.Toast
import com.github.fabtransitionactivity.SheetLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.ankit.gpslibrary.MyTracker
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivityForResult
import android.provider.Settings.Secure
import android.R.string.cancel
import android.app.AlertDialog
import android.content.DialogInterface
import android.location.LocationManager
import android.provider.Settings
import android.support.v4.content.ContextCompat.startActivity
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import org.jetbrains.anko.locationManager


class MainActivity : AppCompatActivity() , SheetLayout.OnFabAnimationEndListener{
    val CAMERA_REQUEST_CODE=0
    val REQUEST_CODE_PERMISSION = 2
    val mPermission : String = Manifest.permission.ACCESS_FINE_LOCATION
    private val REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            showSettingAlert()
        }

        fire.setOnClickListener {
            checkGPS()
        }
        nineOneOne.setOnClickListener {
            checkGPS()
        }
        police.setOnClickListener {
            checkGPS()
        }
        bottom_sheet.setFab(fab)
        bottom_sheet.setFabAnimationEndListener(this)
        fab.setBackgroundColor(Color.parseColor("#ff202f"))

        fab.setOnClickListener {
            bottom_sheet.expandFab()
        }
    }
    override fun onFabAnimationEnd() {
        val intent = Intent(this, InformationActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
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
            REQUEST_CODE ->{
                bottom_sheet.contractFab()
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
                    if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        openCamera()
                    }
                    else{
                        showSettingAlert()
                    }

                }
                else{
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    fun showSettingAlert() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("GPS setting!")
        alertDialog.setMessage("GPS is not enabled, Do you want to go to settings menu? ")
        alertDialog.setPositiveButton("Setting", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            this.startActivity(intent)
        })
        alertDialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        alertDialog.show()
    }
    fun checkGPS(){
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            openCamera()
        }
        else{
            showSettingAlert()
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
