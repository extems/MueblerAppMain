package com.example.mueblerapp20.fragmentsLogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.mueblerapp20.R
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class PantallaTerminosYCond : Fragment() {

    lateinit var v : View

    lateinit var btnAccept : Button
    lateinit var btnDecline : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_terminos_y_cond, container, false)

        btnAccept = v.findViewById(R.id.btn_accept)
        btnDecline = v.findViewById(R.id.btn_decline)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnAccept.setOnClickListener(){

            val toScreenRegistrarse = PantallaTerminosYCondDirections.actionPantallaTerminosYCondToPantallaRegistrarse("vendedor")
            v.findNavController().navigate(toScreenRegistrarse)
        }

        btnDecline.setOnClickListener(){

            val toScreenEleccionUsuario = PantallaTerminosYCondDirections.actionPantallaTerminosYCondToPantallaEleccionUsuario()
            v.findNavController().navigate(toScreenEleccionUsuario)
        }
    }
}