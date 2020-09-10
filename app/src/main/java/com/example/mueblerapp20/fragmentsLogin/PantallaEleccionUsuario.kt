package com.example.mueblerapp20.fragmentsLogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.mueblerapp20.R
import kotlinx.android.synthetic.main.fragment_pantalla_inicio.*

/**
 * A simple [Fragment] subclass.
 */
class PantallaEleccionUsuario : Fragment() {

    lateinit var v : View

    lateinit var btnRegisterSeller : Button
    lateinit var btnRegisterBuyer : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_eleccion_usuario, container, false)

        btnRegisterSeller = v.findViewById(R.id.btn_register_seller)
        btnRegisterBuyer = v.findViewById(R.id.btn_register_buyer)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnRegisterSeller.setOnClickListener(){

            val toScreenTerminosYCond = PantallaEleccionUsuarioDirections.actionPantallaEleccionUsuarioToPantallaTerminosYCond()
            v.findNavController().navigate(toScreenTerminosYCond)

        }

        btnRegisterBuyer.setOnClickListener(){

            val toScreen4 = PantallaEleccionUsuarioDirections.actionPantallaEleccionUsuarioToPantallaRegistrarse()
            v.findNavController().navigate(toScreen4)

        }

    }

}