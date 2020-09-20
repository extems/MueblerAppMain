package com.example.mueblerapp20.fragmentsPublish

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.mueblerapp20.MainActivity
import com.example.mueblerapp20.PublishActivity
import com.example.mueblerapp20.R
import com.example.mueblerapp20.classes.Mueble
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.item_mueble.view.*
import java.util.*
import kotlin.properties.Delegates


class PantallaPublicar : Fragment() {

    lateinit var v : View

    private lateinit var et_nombre: EditText
    private lateinit var et_precio: EditText
    private lateinit var btn_crear_publicacion: Button

    private lateinit var nombre: String
    private var precio by Delegates.notNull<Double>()

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_publicar, container, false)

        et_nombre = v.findViewById(R.id.et_nombre_publicar)
        et_precio = v.findViewById(R.id.et_precio_publicar)
        btn_crear_publicacion = v.findViewById(R.id.btn_crear_publicacion)

        return v
    }

    override fun onStart() {
        super.onStart()

        btn_crear_publicacion.setOnClickListener(){

            nombre = et_nombre.text.toString()
            precio = et_precio.text.toString().toDouble()

            var mueble = Mueble(UUID.randomUUID().toString(),"https://picsum.photos/200",nombre, precio,"Me gusta la papa", 4.99, 123, "Mueble de cocina", "Alpedro", true, 50.00)
            db.collection("muebles").document(mueble.id).set(mueble)

            val toMain = Intent(this.activity, MainActivity::class.java)
            startActivity(toMain)
        }
    }

}