package com.example.mastersh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mastersh.auth.AuthLoginActivity
import com.example.mastersh.auth.AuthSplashActivity
import com.example.mastersh.captureCamera.UploadCaptureCameraActivity
import com.example.mastersh.delivery.DeliveryMainActivity
import com.example.mastersh.realtimeLocation.LocationActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonTutorialAuth = findViewById<Button>(R.id.buttonTutorialAuth)
        buttonTutorialAuth.setOnClickListener {
            startActivity(Intent(this, AuthSplashActivity::class.java))
        }

        val buttonTutorialDelivery = findViewById<Button>(R.id.buttonTutorialDelivery)
        buttonTutorialDelivery.setOnClickListener {
            startActivity(Intent(this, DeliveryMainActivity::class.java))
        }

        val buttonTutorialCapture = findViewById<Button>(R.id.buttonTutorialCapture)
        buttonTutorialCapture.setOnClickListener {
            startActivity(Intent(this, UploadCaptureCameraActivity::class.java))
        }

        val buttonTutorialRealtimeLocation = findViewById<Button>(R.id.buttonTutorialRealtimeLocation)
        buttonTutorialRealtimeLocation.setOnClickListener {
            startActivity(Intent(this, LocationActivity::class.java))
        }
    }
}