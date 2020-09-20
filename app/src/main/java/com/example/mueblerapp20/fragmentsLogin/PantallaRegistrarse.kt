package com.example.mueblerapp20.fragmentsLogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.mueblerapp20.R
import com.google.android.material.snackbar.Snackbar

class PantallaRegistrarse : Fragment() {

    lateinit var v : View

    private lateinit var et_email: EditText
    private lateinit var et_nombre: EditText
    private lateinit var et_contrasena: EditText
    private lateinit var et_confirm_contrasena: EditText
    private lateinit var btnNext : Button

    private lateinit var email: String
    private lateinit var nombre: String
    private lateinit var contrasena: String
    private lateinit var confirm_contrasena: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_registrarse, container, false)

        et_email = v.findViewById(R.id.et_email)
        et_nombre = v.findViewById(R.id.et_nombre)
        et_contrasena = v.findViewById(R.id.et_contrasena)
        et_confirm_contrasena = v.findViewById(R.id.et_confirm_contrasena)
        btnNext = v.findViewById(R.id.btn_registrarse)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnNext.setOnClickListener() {

            if (et_email.length() > 0 && et_nombre.length() > 0 && et_contrasena.length() > 0 && et_confirm_contrasena.length() > 0) {
                email = et_email.text.toString()
                nombre = et_nombre.text.toString()
                contrasena = et_contrasena.text.toString()
                confirm_contrasena = et_confirm_contrasena.text.toString()

                if (contrasena == confirm_contrasena) {
                    var permiso = PantallaRegistrarseArgs.fromBundle(requireArguments()).permiso

                    val toScreenNumeroTelefonico = PantallaRegistrarseDirections.actionPantallaRegistrarseToPantallaNumeroTelefonico(permiso,email,nombre,contrasena)
                    v.findNavController().navigate(toScreenNumeroTelefonico)
                }
                else {
                    Snackbar.make(v, "Confirme correctamente su contraseña", Snackbar.LENGTH_SHORT).show()
                }
            }
            else {
                Snackbar.make(v, "Rellene los campos", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}