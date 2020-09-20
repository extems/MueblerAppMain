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
import com.example.mueblerapp20.classes.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class PantallaNumeroTelefonico : Fragment() {

    lateinit var v : View

    private lateinit var et_numTelefonico: EditText
    private lateinit var btn_registrarse : Button

    private lateinit var numTelefonico: String

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_numero_telefonico, container, false)

        et_numTelefonico = v.findViewById(R.id.et_numTelefonico)
        btn_registrarse = v.findViewById(R.id.btn_registrarse)

        return v
    }

    override fun onStart() {
        super.onStart()

        btn_registrarse.setOnClickListener(){

            if (et_numTelefonico.length() > 0) {
                var permiso = PantallaNumeroTelefonicoArgs.fromBundle(requireArguments()).permiso
                var email = PantallaNumeroTelefonicoArgs.fromBundle(requireArguments()).email
                var nombre = PantallaNumeroTelefonicoArgs.fromBundle(requireArguments()).nombre
                var contrasena = PantallaNumeroTelefonicoArgs.fromBundle(requireArguments()).contrasena
                numTelefonico = et_numTelefonico.text.toString()

                var usuario = Usuario(UUID.randomUUID().toString(),"https://picsum.photos/200",nombre,email,contrasena,numTelefonico.toInt(),permiso,ArrayList<String>(),ArrayList<String>(),ArrayList<String>())
                db.collection("usuarios").document(usuario.id).set(usuario)

                val toScreenLogin = PantallaNumeroTelefonicoDirections.actionPantallaNumeroTelefonicoToPantallaLogin()
                v.findNavController().navigate(toScreenLogin)
            }
            else {
                Snackbar.make(v, "Rellene los campos", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}