package com.example.mueblerapp20.fragmentsMain

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mueblerapp20.R
import com.example.mueblerapp20.classes.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class PantallaCategorias : Fragment() {

    lateinit var v : View

    var usuarioPref : String = ""

    lateinit var tv_sharedPrefs: TextView

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_categorias, container, false)

        tv_sharedPrefs = v.findViewById(R.id.tv_sharedPref)

        return v
    }

    override fun onStart() {
        super.onStart()

        //LEE LA SHARED PREFERENCE
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        usuarioPref = sharedPref.getString("USERID","default")!!

        //AGARRA EL USUARIO DE FIREBASE
        var docRefMail = db.collection("usuarios")
        docRefMail.whereEqualTo("email", usuarioPref).get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot != null) {
                    var usuarioDataSnapshot = dataSnapshot.documents
                    var usuario = usuarioDataSnapshot[0].toObject<Usuario>()!!

                    //RESTO DEL CODIGO DE LA PANTALLA
                    tv_sharedPrefs.text = usuario.email + " " + usuario.nombre + " " + usuario.contrasena + " " + usuario.numeroTelefonico.toString()
                }
            }
            .addOnFailureListener { exception ->
                Snackbar.make(v, "Ha ocurrido un error inesperado", Snackbar.LENGTH_SHORT).show()
            }
    }
}