package com.example.mueblerapp20.fragmentsMain

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mueblerapp20.R
import com.example.mueblerapp20.adapters.MuebleFavoritoListAdapter
import com.example.mueblerapp20.classes.Guest
import com.example.mueblerapp20.classes.Mueble
import com.example.mueblerapp20.classes.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_pantalla_numero_telefonico.*
import java.util.*
import kotlin.properties.Delegates

class PantallaMueble : Fragment() {

    lateinit var v : View

    var userPref : String = ""
    var permission : String = ""

    lateinit var txtNombre : TextView
    lateinit var txtPrecio : TextView
    lateinit var txtDescripcion : TextView
    lateinit var txtCalificacion : TextView
    lateinit var btn_addfav : Button
    lateinit var btn_addcarrito : Button
    lateinit var mueble_seleccionado : String

    lateinit var mueble : Mueble

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

        //LEE LA SHARED PREFERENCE
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        userPref = sharedPref.getString("USERID", "default")!!
        permission = sharedPref.getString("Permiso", "default")!!

        //AGARRA EL USUARIO DE FIREBASE
        if (permission == "Vendedor") {
            var docRefMail = db.collection("usuarios")
            docRefMail.whereEqualTo("email", userPref).get()
                .addOnSuccessListener { dataSnapshot ->
                    if (dataSnapshot != null) {
                        var usuarioDataSnapshot = dataSnapshot.documents
                        var usuario = usuarioDataSnapshot[0].toObject<Usuario>()!!

                        //RESTO DEL CODIGO DE LA PANTALLA
                        mueble_seleccionado =
                            PantallaMuebleArgs.fromBundle(requireArguments()).idMuebleSeleccionado

                        var docRef = db.collection("muebles")
                        docRef.whereEqualTo("id", mueble_seleccionado).get()
                            .addOnSuccessListener { dataSnapshot ->
                                if (dataSnapshot != null) {
                                    var mueblesDataSnapshot = dataSnapshot.documents

                                    mueble = mueblesDataSnapshot[0].toObject<Mueble>()!!
                                    txtNombre.text = mueble.nombre
                                    txtPrecio.text = mueble.precio.toString()
                                    txtDescripcion.text = mueble.descripcion
                                    txtCalificacion.text = mueble.calificacion.toString()

                                    btn_addfav.setOnClickListener() {

                                        usuario.favoritos.add(mueble_seleccionado)
                                        db.collection("usuarios")
                                            .whereEqualTo("email", usuario.email).get()
                                            .addOnSuccessListener { dataSnapshot ->
                                                if (dataSnapshot != null) {
                                                    var usuarioDataSnapshot = dataSnapshot.documents
                                                    db.collection("usuarios")
                                                        .document(usuarioDataSnapshot[0].id)
                                                        .set(usuario)
                                                    Snackbar.make(
                                                        v,
                                                        "Se añadio a favoritos",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Log.d("Test", "No such document")
                                                }
                                            }
                                            .addOnFailureListener { exception ->
                                                Log.d("Test", "get failed with ", exception)
                                            }
                                    }

                                    btn_addcarrito.setOnClickListener() {

                                        usuario.carrito.add(mueble_seleccionado)
                                        db.collection("usuarios")
                                            .whereEqualTo("email", usuario.email).get()
                                            .addOnSuccessListener { dataSnapshot ->
                                                if (dataSnapshot != null) {
                                                    var usuarioDataSnapshot = dataSnapshot.documents
                                                    db.collection("usuarios")
                                                        .document(usuarioDataSnapshot[0].id)
                                                        .set(usuario)
                                                    Snackbar.make(
                                                        v,
                                                        "Se añadio a carrito",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Log.d("Test", "No such document")
                                                }
                                            }
                                            .addOnFailureListener { exception ->
                                                Log.d("Test", "get failed with ", exception)
                                            }
                                    }
                                } else {
                                    Log.d("Test", "No such document")
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.d("Test", "get failed with ", exception)
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Snackbar.make(v, "Ha ocurrido un error inesperado", Snackbar.LENGTH_SHORT)
                        .show()
                }
        }

        if (permission == "Invitado") {
            var docRefMail = db.collection("guest")
            docRefMail.whereEqualTo("idguest", userPref).get()
                .addOnSuccessListener { dataSnapshot ->
                    if (dataSnapshot != null) {
                        var guestDataSnapshot = dataSnapshot.documents
                        var guest = guestDataSnapshot[0].toObject<Guest>()!!

                        //RESTO DEL CODIGO DE LA PANTALLA
                        mueble_seleccionado =
                            PantallaMuebleArgs.fromBundle(requireArguments()).idMuebleSeleccionado

                        var docRef = db.collection("muebles")
                        docRef.whereEqualTo("id", mueble_seleccionado).get()
                            .addOnSuccessListener { dataSnapshot ->
                                if (dataSnapshot != null) {
                                    var mueblesDataSnapshot = dataSnapshot.documents

                                    mueble = mueblesDataSnapshot[0].toObject<Mueble>()!!
                                    txtNombre.text = mueble.nombre
                                    txtPrecio.text = mueble.precio.toString()
                                    txtDescripcion.text = mueble.descripcion
                                    txtCalificacion.text = mueble.calificacion.toString()

                                    btn_addfav.setOnClickListener() {

                                        guest.favoritos.add(mueble_seleccionado)
                                        db.collection("guest")
                                            .whereEqualTo("idguest", guest.idguest).get()
                                            .addOnSuccessListener { dataSnapshot ->
                                                if (dataSnapshot != null) {
                                                    var guestDataSnapshot = dataSnapshot.documents
                                                    db.collection("guest")
                                                        .document(guestDataSnapshot[0].id)
                                                        .set(guest)
                                                    Snackbar.make(
                                                        v,
                                                        "Se añadio a favoritos",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Log.d("Test", "No such document")
                                                }
                                            }
                                            .addOnFailureListener { exception ->
                                                Log.d("Test", "get failed with ", exception)
                                            }
                                    }

                                    btn_addcarrito.setOnClickListener() {

                                        guest.carrito.add(mueble_seleccionado)
                                        db.collection("guest")
                                            .whereEqualTo("idguest", guest.idguest).get()
                                            .addOnSuccessListener { dataSnapshot ->
                                                if (dataSnapshot != null) {
                                                    var guestDataSnapshot = dataSnapshot.documents
                                                    db.collection("guest")
                                                        .document(guestDataSnapshot[0].id)
                                                        .set(guest)
                                                    Snackbar.make(
                                                        v,
                                                        "Se añadio a carrito",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Log.d("Test", "No such document")
                                                }
                                            }
                                            .addOnFailureListener { exception ->
                                                Log.d("Test", "get failed with ", exception)
                                            }
                                    }
                                } else {
                                    Log.d("Test", "No such document")
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.d("Test", "get failed with ", exception)
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Snackbar.make(v, "Ha ocurrido un error inesperado", Snackbar.LENGTH_SHORT)
                        .show()
                }
        }
    }
}