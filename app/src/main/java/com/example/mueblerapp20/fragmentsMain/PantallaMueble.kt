package com.example.mueblerapp20.fragmentsMain

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.mueblerapp20.R
import com.example.mueblerapp20.classes.Mueble
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_pantalla_numero_telefonico.*
import java.util.*
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
class PantallaMueble : Fragment() {

    lateinit var v : View

    lateinit var txtNombre : TextView
    lateinit var txtPrecio : TextView
    lateinit var txtDescripcion : TextView
    lateinit var txtCalificacion : TextView
    lateinit var btn_addfav : Button
    lateinit var btn_addcarrito : Button
    lateinit var mueble_seleccionado : String

    lateinit var mueble : Mueble
    lateinit var document : String

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_mueble, container, false)

        txtNombre = v.findViewById(R.id.textNombre)
        txtPrecio = v.findViewById(R.id.textPrecio)
        txtDescripcion = v.findViewById(R.id.textDescripcion)
        txtCalificacion = v.findViewById(R.id.textCalificacion)
        btn_addfav = v.findViewById(R.id.btn_addfav)
        btn_addcarrito = v.findViewById(R.id.btn_addcarrito)

        return v
    }

    override fun onStart() {
        super.onStart()

        mueble_seleccionado = PantallaMuebleArgs.fromBundle(requireArguments()).nombreMuebleSeleccionado

        var docRef = db.collection("muebles")
        docRef.whereEqualTo("nombre", mueble_seleccionado).get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot != null) {
                    var mueblesDataSnapshot = dataSnapshot.documents

                    document = mueblesDataSnapshot[0].id

                    mueble = mueblesDataSnapshot[0].toObject<Mueble>()!!
                    if (mueble != null) {
                        txtNombre.text = mueble.nombre
                        txtPrecio.text = mueble.precio.toString()
                        txtDescripcion.text = mueble.descripcion
                        txtCalificacion.text = mueble.calificacion.toString()
                    } else {
                        Log.d("Test", "document is empty")
                    }
                } else {
                    Log.d("Test", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Test", "get failed with ", exception)
            }


//        btn_addfav.setOnClickListener(){
//
//            db.collection("muebles").document(document).set(mueble)
//            Snackbar.make(v, "Se añadio a favoritos", Snackbar.LENGTH_SHORT).show()
//        }
//
//        btn_addcarrito.setOnClickListener(){
//
//
//            db.collection("muebles").document(document).set(mueble)
//            Snackbar.make(v, "Se añadio a carrito", Snackbar.LENGTH_SHORT).show()
//        }
    }
}