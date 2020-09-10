package com.example.mueblerapp20.fragmentsMain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mueblerapp20.R

/**
 * A simple [Fragment] subclass.
 */

class PantallaCategorias : Fragment() {

    lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_categorias, container, false)



        return v
    }

}