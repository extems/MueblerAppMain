package com.example.mueblerapp20.fragmentsMain


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mueblerapp20.R
import com.example.mueblerapp20.adapters.MuebleListAdapter
import com.example.mueblerapp20.classes.Mueble
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass.
 */

class PantallaInicio : Fragment() {

    lateinit var v: View

    lateinit var recMuebles: RecyclerView

    var muebles: MutableList<Mueble> = ArrayList<Mueble>()

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var muebleListAdapter: MuebleListAdapter

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_pantalla_inicio, container, false)

        recMuebles = v.findViewById(R.id.rec_muebles)

        return v
    }

    override fun onStart() {
        super.onStart()

        var docRef = db.collection("muebles")
        docRef.get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot != null) {
                    var mueblesDataSnapshot = dataSnapshot.documents
                    for (snapshot in mueblesDataSnapshot) {
                        snapshot.toObject<Mueble>()?.let { muebles.add(it) }
                    }
                    recMuebles.setHasFixedSize(true)

                    gridLayoutManager = GridLayoutManager(context, 2)
                    recMuebles.layoutManager = gridLayoutManager

                    muebleListAdapter = MuebleListAdapter(muebles, requireContext()) { position ->
                        onItemClick(position)
                    }

                    recMuebles.adapter = muebleListAdapter
                } else {
                    Log.d("Test", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Test", "get failed with ", exception)
            }
    }


    fun onItemClick ( position : Int ) {
        val toScreen2 = PantallaInicioDirections.actionPantallaInicioToPantallaMueble(muebles[position].nombre)
        v.findNavController().navigate(toScreen2)
    }

}

