package com.example.mueblerapp20.fragmentsMain

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mueblerapp20.R
import com.example.mueblerapp20.adapters.MuebleFavoritoListAdapter
import com.example.mueblerapp20.adapters.MuebleListAdapter
import com.example.mueblerapp20.classes.Mueble
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
class PantallaFavoritos : Fragment() {

    lateinit var v : View

    lateinit var recMueblesFavoritos : RecyclerView

    var mueblesfavoritos : MutableList <Mueble> = ArrayList <Mueble> ()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var muebleFavoritoListAdapter : MuebleFavoritoListAdapter

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_favoritos, container, false)

        recMueblesFavoritos = v.findViewById(R.id.rec_favoritos)

        return v
    }

    override fun onStart() {
        super.onStart()

       /* mueblesfavoritos.add(Mueble("papopepo","Mueble Blanco, compralo", 19.99,"Me gusta la papa", 4.99, 123, "Mueble de cocina", "Alpedro", true, 99.98))
        mueblesfavoritos.add(Mueble("papopepo","Mueble Blanco, terrible mueble", 120.00,"Me gusta la papa", 4.99, 123, "Mueble de cocina", "Alpedro", true, 99.98))
        mueblesfavoritos.add(Mueble("papopepo","Mueble Blanco muy caro", 999.99,"Me gusta la papa", 4.99, 123, "Mueble de cocina", "Alpedro", true, 99.98))
        mueblesfavoritos.add(Mueble("papopepo","Mueble Blanco con patas marrones", 98.76,"Me gusta la papa", 4.99, 123, "Mueble de cocina", "Alpedro", true, 99.98))
        mueblesfavoritos.add(Mueble("papopepo","Mueble Blanco triste", 138.02,"Me gusta la papa", 4.99, 123, "Mueble de cocina", "Alpedro", true, 99.98))
*/
        var docRef = db.collection("muebles")
        docRef.whereEqualTo("favorito", true).get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot != null) {
                    var mueblesDataSnapshot = dataSnapshot.documents
                    for (snapshot in mueblesDataSnapshot) {
                        snapshot.toObject<Mueble>()?.let { mueblesfavoritos.add(it) }
                    }
                    recMueblesFavoritos.setHasFixedSize(true)

                    linearLayoutManager = LinearLayoutManager(context)
                    recMueblesFavoritos.layoutManager = linearLayoutManager

                    muebleFavoritoListAdapter = MuebleFavoritoListAdapter(mueblesfavoritos, requireContext()){position -> onItemClick(position)}

                    recMueblesFavoritos.adapter = muebleFavoritoListAdapter
                } else {
                    Log.d("Test", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Test", "get failed with ", exception)
            }
    }

    fun onItemClick ( position : Int ) {
        val toScreen2 = PantallaFavoritosDirections.actionPantallaFavoritosToPantallaMueble(mueblesfavoritos[position].nombre)
        v.findNavController().navigate(toScreen2)
    }
}