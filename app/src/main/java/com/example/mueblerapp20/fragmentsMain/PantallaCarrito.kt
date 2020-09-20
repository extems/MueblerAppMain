package com.example.mueblerapp20.fragmentsMain

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mueblerapp20.R
import com.example.mueblerapp20.adapters.MuebleFavoritoListAdapter
import com.example.mueblerapp20.classes.Guest
import com.example.mueblerapp20.classes.Mueble
import com.example.mueblerapp20.classes.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class PantallaCarrito : Fragment() {

    lateinit var v : View

    var userPref : String = ""
    var permission : String = ""

    lateinit var recCarrito : RecyclerView

    var mueblescarrito : MutableList <Mueble> = ArrayList <Mueble> ()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var muebleFavoritoListAdapter : MuebleFavoritoListAdapter

    private lateinit var tv_mueble1: TextView
    private lateinit var tv_mueble2: TextView
    private lateinit var tv_mueble3: TextView
    private lateinit var precio_subtotal: TextView
    private lateinit var precio_envio: TextView
    private lateinit var precio_total: TextView

    private var subtotal: Double = 0.00
    private var envio: Double = 0.00

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_carrito, container, false)

        tv_mueble1 = v.findViewById(R.id.tv_mueble1)
        tv_mueble2 = v.findViewById(R.id.tv_mueble2)
        tv_mueble3 = v.findViewById(R.id.tv_mueble3)
        precio_subtotal = v.findViewById(R.id.precio_subtotal)
        precio_envio = v.findViewById(R.id.precio_envio)
        precio_total = v.findViewById(R.id.precio_total)
        recCarrito = v.findViewById(R.id.rec_carrito)

        return v
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        //LEE LA SHARED PREFERENCE
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        userPref = sharedPref.getString("USERID","default")!!
        permission = sharedPref.getString("Permiso","default")!!


        if (permission == "Vendedor") {
            //AGARRA EL USUARIO REGISTRADO DE FIREBASE
            var docRefMail = db.collection("usuarios")
            docRefMail.whereEqualTo("email", userPref).get()
                .addOnSuccessListener { dataSnapshot ->
                    if (dataSnapshot != null) {
                        var usuarioDataSnapshot = dataSnapshot.documents
                        var usuario = usuarioDataSnapshot[0].toObject<Usuario>()!!

                        //RESTO DEL CODIGO DE LA PANTALLA
                        if (usuario.carrito.isEmpty())
                        {
                            tv_mueble1.text = "No tienes ningún mueble en el carrito"
                        }
                        else {
                            for (mueblecarrito in usuario.carrito)
                            {
                                var docRef = db.collection("muebles").document(mueblecarrito)
                                docRef.get().addOnSuccessListener { dataSnapshot ->
                                    if (dataSnapshot != null) {
                                        val mueble_carrito = dataSnapshot.toObject<Mueble>()
                                        if (mueble_carrito != null) {
                                            mueblescarrito.add(mueble_carrito)
                                        }
                                        recCarrito.setHasFixedSize(true)

                                        linearLayoutManager = LinearLayoutManager(context)
                                        recCarrito.layoutManager = linearLayoutManager

                                        muebleFavoritoListAdapter = MuebleFavoritoListAdapter(mueblescarrito, requireContext()){position -> onItemClick(position)}

                                        recCarrito.adapter = muebleFavoritoListAdapter

                                        when (mueblescarrito.size) {
                                            1 -> {
                                                tv_mueble1.text = mueblescarrito[0].nombre + " $" + mueblescarrito[0].precio.toString()
                                            }
                                            2 -> {
                                                tv_mueble1.text = mueblescarrito[0].nombre + " $" + mueblescarrito[0].precio.toString()
                                                tv_mueble2.text = mueblescarrito[1].nombre + " $" + mueblescarrito[1].precio.toString()
                                            }
                                            3 -> {
                                                tv_mueble1.text = mueblescarrito[0].nombre + " $" + mueblescarrito[0].precio.toString()
                                                tv_mueble2.text = mueblescarrito[1].nombre + " $" + mueblescarrito[1].precio.toString()
                                                tv_mueble3.text = mueblescarrito[2].nombre + " $" + mueblescarrito[2].precio.toString()
                                            }
                                            else -> {
                                                tv_mueble1.text = mueblescarrito[0].nombre + " $" + mueblescarrito[0].precio.toString()
                                                tv_mueble2.text = mueblescarrito[1].nombre + " $" + mueblescarrito[1].precio.toString()
                                                tv_mueble3.text = (mueblescarrito.size-2).toString() + " muebles más"
                                            }
                                        }

                                        subtotal = 0.00
                                        envio = 0.00
                                        for (mueble in mueblescarrito)
                                        {
                                            subtotal += mueble.precio
                                            envio += mueble.costoDeEnvio
                                        }
                                        precio_subtotal.text = "$$subtotal"
                                        precio_envio.text = "$$envio"
                                        precio_total.text = "$" + (subtotal+envio).toString()

                                    } else {
                                        Log.d("Test", "No such document")
                                    }
                                }
                                    .addOnFailureListener { exception ->
                                        Log.d("Test", "get failed with ", exception)
                                    }
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Snackbar.make(v, "Ha ocurrido un error inesperado", Snackbar.LENGTH_SHORT).show()
                }
        }

        if (permission == "Invitado"){
            //AGARRA EL USUARIO REGISTRADO DE FIREBASE
            var docRefMail = db.collection("guest")
            docRefMail.whereEqualTo("idguest", userPref).get()
                .addOnSuccessListener { dataSnapshot ->
                    if (dataSnapshot != null) {
                        var guestDataSnapshot = dataSnapshot.documents
                        var guest = guestDataSnapshot[0].toObject<Guest>()!!

                        //RESTO DEL CODIGO DE LA PANTALLA
                        if (guest.carrito.isEmpty())
                        {
                            tv_mueble1.text = "No tienes ningún mueble en el carrito"
                        }
                        else {
                            for (mueblecarrito in guest.carrito)
                            {
                                var docRef = db.collection("muebles").document(mueblecarrito)
                                docRef.get().addOnSuccessListener { dataSnapshot ->
                                    if (dataSnapshot != null) {
                                        val mueble_carrito = dataSnapshot.toObject<Mueble>()
                                        if (mueble_carrito != null) {
                                            mueblescarrito.add(mueble_carrito)
                                        }
                                        recCarrito.setHasFixedSize(true)

                                        linearLayoutManager = LinearLayoutManager(context)
                                        recCarrito.layoutManager = linearLayoutManager

                                        muebleFavoritoListAdapter = MuebleFavoritoListAdapter(mueblescarrito, requireContext()){position -> onItemClick(position)}

                                        recCarrito.adapter = muebleFavoritoListAdapter

                                        when (mueblescarrito.size) {
                                            1 -> {
                                                tv_mueble1.text = mueblescarrito[0].nombre + " $" + mueblescarrito[0].precio.toString()
                                            }
                                            2 -> {
                                                tv_mueble1.text = mueblescarrito[0].nombre + " $" + mueblescarrito[0].precio.toString()
                                                tv_mueble2.text = mueblescarrito[1].nombre + " $" + mueblescarrito[1].precio.toString()
                                            }
                                            3 -> {
                                                tv_mueble1.text = mueblescarrito[0].nombre + " $" + mueblescarrito[0].precio.toString()
                                                tv_mueble2.text = mueblescarrito[1].nombre + " $" + mueblescarrito[1].precio.toString()
                                                tv_mueble3.text = mueblescarrito[2].nombre + " $" + mueblescarrito[2].precio.toString()
                                            }
                                            else -> {
                                                tv_mueble1.text = mueblescarrito[0].nombre + " $" + mueblescarrito[0].precio.toString()
                                                tv_mueble2.text = mueblescarrito[1].nombre + " $" + mueblescarrito[1].precio.toString()
                                                tv_mueble3.text = (mueblescarrito.size-2).toString() + " muebles más"
                                            }
                                        }

                                        subtotal = 0.00
                                        envio = 0.00
                                        for (mueble in mueblescarrito)
                                        {
                                            subtotal += mueble.precio
                                            envio += mueble.costoDeEnvio
                                        }
                                        precio_subtotal.text = "$$subtotal"
                                        precio_envio.text = "$$envio"
                                        precio_total.text = "$" + (subtotal+envio).toString()

                                    } else {
                                        Log.d("Test", "No such document")
                                    }
                                }
                                    .addOnFailureListener { exception ->
                                        Log.d("Test", "get failed with ", exception)
                                    }
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Snackbar.make(v, "Ha ocurrido un error inesperado", Snackbar.LENGTH_SHORT).show()
                }
        }


    }

    fun onItemClick ( position : Int ) {
        val toScreen2 = PantallaCarritoDirections.actionPantallaCarritoToPantallaMueble(mueblescarrito[position].id)
        v.findNavController().navigate(toScreen2)
    }
}