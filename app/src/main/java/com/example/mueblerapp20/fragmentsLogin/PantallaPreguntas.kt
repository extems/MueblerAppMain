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
class PantallaPreguntas : Fragment() {

    lateinit var v : View

    lateinit var btnGoBack : Button
    lateinit var btnRegister : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_preguntas, container, false)

        btnGoBack = v.findViewById(R.id.btn_goback)
        btnRegister = v.findViewById(R.id.btn_register)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnGoBack.setOnClickListener(){

            val toScreen2 = PantallaPreguntasDirections.actionPantallaPreguntasToPantallaNumeroTelefonico()
            v.findNavController().navigate(toScreen2)

        }


        btnRegister.setOnClickListener(){

            val toScreen3 = PantallaPreguntasDirections.actionPantallaPreguntasToPantallaLogin()
            v.findNavController().navigate(toScreen3)

        }

    }

}