package com.example.mueblerapp20.fragmentsMain

import android.content.Context
import android.content.SharedPreferences
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
import com.example.mueblerapp20.classes.Guest
import com.example.mueblerapp20.classes.Mueble
import com.example.mueblerapp20.classes.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
class PantallaFavoritos : Fragment() {

    lateinit var v : View

    var userPref : String = ""
    var permission : String = ""

    lateinit var recMueblesFavoritos : RecyclerView

    var mueblesfavoritos : MutableList <Mueble> = ArrayList <Mueble>()

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

        //LEE LA SHARED PREFERENCE
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        userPref = sharedPref.getString("USERID","default")!!
        permission = sharedPref.getString("Permiso","default")!!

        //AGARRA EL USUARIO DE FIREBASE
        if(permission == "Vendedor") {
            var docRefMail = db.collection("usuarios")
            docRefMail.whereEqualTo("email", userPref).get()
                .addOnSuccessListener { dataSnapshot ->
                    if (dataSnapshot != null) {
                        var usuarioDataSnapshot = dataSnapshot.documents
                        var usuario = usuarioDataSnapshot[0].toObject<Usuario>()!!

                        //RESTO DEL CODIGO DE LA PANTALLA
                        for (mueblefavorito in usuario.favoritos) {
                            var docRef = db.collection("muebles").document(mueblefavorito)
                            docRef.get()
                                .addOnSuccessListener { dataSnapshot ->
                                    if (dataSnapshot != null) {
                                        val mueble_favorito = dataSnapshot.toObject<Mueble>()
                                        if (mueble_favorito != null) {
                                            mueblesfavoritos.add(mueble_favorito)
                                        }
                                        recMueblesFavoritos.setHasFixedSize(true)

                                        linearLayoutManager = LinearLayoutManager(context)
                                        recMueblesFavoritos.layoutManager = linearLayoutManager

                                        muebleFavoritoListAdapter = MuebleFavoritoListAdapter(
                                            mueblesfavoritos,
                                            requireContext()
                                        ) { position -> onItemClick(position) }

                                        recMueblesFavoritos.adapter = muebleFavoritoListAdapter
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
                .addOnFailureListener { exception ->
                    Snackbar.make(v, "Ha ocurrido un error inesperado", Snackbar.LENGTH_SHORT)
                        .show()
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
                            for (mueblefavorito in guest.favoritos)
                            {
                                var docRef = db.collection("muebles").document(mueblefavorito)
                                docRef.get().addOnSuccessListener { dataSnapshot ->
                                    if (dataSnapshot != null) {
                                        val mueble_favorito = dataSnapshot.toObject<Mueble>()
                                        if (mueble_favorito != null) {
                                            mueblesfavoritos.add(mueble_favorito)
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
                    }
                }
                .addOnFailureListener { exception ->
                    Snackbar.make(v, "Ha ocurrido un error inesperado", Snackbar.LENGTH_SHORT).show()
                }
        }

    }

    fun onItemClick ( position : Int ) {
        val toScreen2 = PantallaFavoritosDirections.actionPantallaFavoritosToPantallaMueble(mueblesfavoritos[position].id)
        v.findNavController().navigate(toScreen2)
    }
}