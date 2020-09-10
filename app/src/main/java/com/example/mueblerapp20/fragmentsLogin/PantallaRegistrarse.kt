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
class PantallaRegistrarse : Fragment() {

    lateinit var v : View

    lateinit var btnNext : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_registrarse, container, false)

        btnNext = v.findViewById(R.id.btn_next)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnNext.setOnClickListener(){

            val toScreen4 = PantallaRegistrarseDirections.actionPantallaRegistrarseToPantallaNumeroTelefonico()
            v.findNavController().navigate(toScreen4)

        }


    }

}