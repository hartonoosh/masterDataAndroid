package com.example.mastersh.realtimeLocation

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.mastersh.R
import com.example.mastersh.auth.model.SessionManager
import com.example.mastersh.delivery.presenter.PresenterDelivery
import com.example.mastersh.realtimeLocation.presenter.PresenterLocation
import com.example.mastersh.realtimeLocation.view.LocationView
import com.google.android.gms.location.*

class LocationActivity : AppCompatActivity(), LocationView {
    // untuk mengimpan data latlong
    lateinit var Latitude: String
    lateinit var Longitude: String

    var tvLatitude: TextView? = null
    var tvLongitude: TextView? = null
    lateinit var btnStart: Button
    lateinit var btnStop: Button

    // mengambil lokasi GPS
    // permission hanya integer unik, dan dapat dirubah menjadi berapa pun
    private var PERMISSION_ID = 1000
    // membuat variable yang kita perlukan
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    lateinit var handler: Handler

    //deklarasikan variabel session
    private lateinit var sessionManager: SessionManager

    private lateinit var presenterLocation: PresenterLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        sessionManager = SessionManager(this)
        presenterLocation = PresenterLocation(this)

        // intiate pada fused providerclient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        handler = Handler()

        tvLatitude = findViewById<View>(R.id.txtLocationLatitude) as TextView
        tvLongitude = findViewById<View>(R.id.txtLocaationLongitude) as TextView
        btnStart = findViewById(R.id.btnLocationStart)
        btnStop = findViewById(R.id.btnLocationStop)

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                getLastLocation() // untuk mendapatkan lokasi terakhir
                val token = sessionManager.fetchAuthToken()
                val token_user = token.toString().trim()
//                presenterLocation.save_location_realtime(
//                    token_user,
//                    Latitude,
//                    Longitude
//                )
                handler.postDelayed(this, 10000)
            }
        }
        btnStart.setOnClickListener {
            handler.postDelayed(runnable, 1000)
        }
        btnStop.setOnClickListener {
            handler.removeCallbacks(runnable)
            Toast.makeText(this, "Lokasi Realtime Stop", Toast.LENGTH_LONG).show()
        }


    }
    // membuat fungsi untuk mengijinkan kita mendapatkan lokasi terakhir di hp kita
    private fun getLastLocation(){
        // pertama kita cek permission dulu
        if (checkPermission()){
            // sekarang cek lokasi enable atau tidak
            if (isLocationEnabled()){
                // sekarang dapatkan lokasinya
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location = task.result
                    if (location == null){
                        // jika lokasinya null kita kaan ambil lokasi user baru
                        // jangan lupa tambahkan lokasi user baru
                        getNewLocation()
                    }else{
                        //location.latitude akan mengambil titik koordinan latitude
                        //location.longitude akan mengambil titik koordinat longitude
                        tvLongitude?.text = location.longitude.toString()
                        tvLatitude?.text = location.latitude.toString()

                        Latitude = location.latitude.toString()
                        Longitude = location.longitude.toString()

                        Toast.makeText(this, "Koordinat Anda = "+Latitude+" , "+Longitude, Toast.LENGTH_LONG).show()
                    }
                }
            }else {
                Toast.makeText(this, "Tolong Aktifkan Lokasi Anda", Toast.LENGTH_SHORT).show()
            }
        }else{
            requestPermission()
        }
    }

    // membuat user mengaktifkan lokasi pada HP nya untuk yang pertama kali pakai
    private fun getNewLocation(){
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,locationCallback, Looper.myLooper()
            // sekarang buat variable locationCallBack
        )
    }

    // mengambil data lokasi terakhir saat baru pertama masuk
    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation = p0.lastLocation
            // sekarang atur lokasi baru
            tvLongitude?.text = lastLocation.longitude.toString()
            tvLatitude?.text = lastLocation.latitude.toString()

            Latitude = lastLocation.latitude.toString()
            Longitude = lastLocation.longitude.toString()

            showToast()
        }
    }

    // menampilkan notifikasi koordinat terakhir dari hp
    private fun showToast() {
        Toast.makeText(this, "Koordinat Anda = "+Latitude+" , "+Longitude, Toast.LENGTH_LONG).show()
    }

    // mengecek permission lokasi
    private fun checkPermission():Boolean{
        if (
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }

    // mengecek untuk mengijinkan user permission
    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSION_ID
        )
    }

    // mengecek apakah lokasi di HP sudah aktif atau belum
    private fun isLocationEnabled():Boolean{
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    // untuk mengecek perijinan
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // fungsi untuk cek permission result
        // kita gunakan untuk mendebug kode
        if(requestCode == PERMISSION_ID){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d( "Debug:", "You Have the Permission")
            }
        }
    }

    override fun suksesPOSTLOCATION(msg: String) {
        TODO("Not yet implemented")
    }

    override fun gagalPOSTLOCATION(msg: String) {
        TODO("Not yet implemented")
    }
}