package com.example.mueblerapp20.fragmentsMain

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mueblerapp20.R
import com.example.mueblerapp20.adapters.MuebleFavoritoListAdapter
import com.example.mueblerapp20.classes.Mueble
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass.
 */
class PantallaCarrito : Fragment() {

    lateinit var v : View

    lateinit var recCarrito : RecyclerView

    var mueblescarrito : MutableList <Mueble> = ArrayList <Mueble> ()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var muebleFavoritoListAdapter : MuebleFavoritoListAdapter

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_carrito, container, false)

        recCarrito = v.findViewById(R.id.rec_carrito)

        return v
    }

    override fun onStart() {
        super.onStart()

//        var docRef = db.collection("muebles")
//        docRef.whereEqualTo().get()
//            .addOnSuccessListener { dataSnapshot ->
//                if (dataSnapshot != null) {
//                    var mueblesDataSnapshot = dataSnapshot.documents
//                    for (snapshot in mueblesDataSnapshot) {
//                        snapshot.toObject<Mueble>()?.let { mueblescarrito.add(it) }
//                    }
//                    recCarrito.setHasFixedSize(true)
//
//                    linearLayoutManager = LinearLayoutManager(context)
//                    recCarrito.layoutManager = linearLayoutManager
//
//                    muebleFavoritoListAdapter = MuebleFavoritoListAdapter(mueblescarrito, requireContext()){position -> onItemClick(position)}
//
//                    recCarrito.adapter = muebleFavoritoListAdapter
//                } else {
//                    Log.d("Test", "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("Test", "get failed with ", exception)
//            }
    }

    fun onItemClick ( position : Int ) {
        val toScreen2 = PantallaCarritoDirections.actionPantallaCarritoToPantallaMueble(mueblescarrito[position].nombre)
        v.findNavController().navigate(toScreen2)
    }
}