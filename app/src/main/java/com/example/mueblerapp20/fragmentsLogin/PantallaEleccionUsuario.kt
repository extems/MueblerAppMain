package com.example.mueblerapp20.fragmentsLogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.mueblerapp20.R

/**
 * A simple [Fragment] subclass.
 */
class PantallaEleccionUsuario : Fragment() {

    lateinit var v : View

    lateinit var btnGoBack : Button
    lateinit var btnRegisterSeller : Button
    lateinit var btnRegisterBuyer : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_eleccion_usuario, container, false)

        btnGoBack = v.findViewById(R.id.btn_goback_login)
        btnRegisterSeller = v.findViewById(R.id.btn_register_seller)
        btnRegisterBuyer = v.findViewById(R.id.btn_register_buyer)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnGoBack.setOnClickListener(){

            val toScreen2 = PantallaEleccionUsuarioDirections.actionPantallaEleccionUsuarioToPantallaLogin()
            v.findNavController().navigate(toScreen2)

        }


        btnRegisterSeller.setOnClickListener(){

            val toScreen3 = PantallaEleccionUsuarioDirections.actionPantallaEleccionUsuarioToPantallaTerminosYCond()
            v.findNavController().navigate(toScreen3)

        }

        btnRegisterBuyer.setOnClickListener(){

            // val buyerSelected = 1
            val toScreen4 = PantallaEleccionUsuarioDirections.actionPantallaEleccionUsuarioToPantallaRegistrarse()
            v.findNavController().navigate(toScreen4)

        }

    }

}