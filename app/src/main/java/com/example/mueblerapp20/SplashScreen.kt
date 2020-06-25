package com.example.mueblerapp20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    private val SplashTimeOut : Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this,LoginActivity::class.java)) //Inicio el Main
            finish() //Elimino la actual
        },SplashTimeOut) //Después de esperar TIME
    }

}