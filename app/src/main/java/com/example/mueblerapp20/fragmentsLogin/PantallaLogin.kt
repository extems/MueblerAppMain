package com.example.mueblerapp20.fragmentsLogin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.findNavController
import com.example.mueblerapp20.MainActivity
import com.example.mueblerapp20.R

/**
 * A simple [Fragment] subclass.
 */

class PantallaLogin : Fragment() {

    lateinit var v : View

    lateinit var btnChangePassword : Button
    lateinit var btnLogin : Button
    lateinit var btnRegister : Button
    lateinit var btnLoginGuest : Button
    lateinit var btnLoginGoogle : ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_login, container, false)

        btnChangePassword = v.findViewById(R.id.btn_change_password)
        btnLogin = v.findViewById(R.id.btn_login)
        btnRegister = v.findViewById(R.id.btn_register)
        btnLoginGuest = v.findViewById(R.id.btn_login_guest)
        btnLoginGoogle = v.findViewById(R.id.btn_login_google)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnChangePassword.setOnClickListener(){

            val toScreen2 = PantallaLoginDirections.actionPantallaLoginToPantallaCambiarContrasena()
            v.findNavController().navigate(toScreen2)

        }

        btnRegister.setOnClickListener(){

            val toScreen3 = PantallaLoginDirections.actionPantallaLoginToPantallaEleccionUsuario(true,false)
            v.findNavController().navigate(toScreen3)

        }

        btnLogin.setOnClickListener(){

            val toMain = Intent(this.activity, MainActivity::class.java)
            startActivity(toMain)

        }
    }

}