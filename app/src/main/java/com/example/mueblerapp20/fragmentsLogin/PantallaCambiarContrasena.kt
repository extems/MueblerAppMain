package com.example.mueblerapp20.fragmentsLogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.mueblerapp20.R

class PantallaCambiarContrasena : Fragment() {

    lateinit var v : View

    lateinit var btnChangePassword : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_cambiar_contrasena, container, false)

        btnChangePassword = v.findViewById(R.id.btn_change_password)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnChangePassword.setOnClickListener(){

            val toScreenLogin = PantallaCambiarContrasenaDirections.actionPantallaCambiarContrasenaToPantallaLogin()
            v.findNavController().navigate(toScreenLogin)

        }

    }

}