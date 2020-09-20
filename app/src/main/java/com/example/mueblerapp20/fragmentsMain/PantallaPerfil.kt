package com.example.mueblerapp20.fragmentsMain

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.mueblerapp20.LoginActivity
import com.example.mueblerapp20.MainActivity
import com.example.mueblerapp20.PublishActivity
import com.example.mueblerapp20.R
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */

class PantallaPerfil : Fragment() {

    lateinit var v : View

    private lateinit var btn_a_publicar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_perfil, container, false)

        val toolbar = v.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.perfil_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = when(item.itemId) {

            R.id.action_edit -> {
                Snackbar.make(v,"Esta caracteristica se encuentra deshabilitada",Snackbar.LENGTH_SHORT).show()
            }

            R.id.action_add ->{
                val toPublish = Intent(this.activity, PublishActivity::class.java)
                startActivity(toPublish)
            }

            R.id.action_logout -> {
                val toLogin = Intent(this.activity, LoginActivity::class.java)
                startActivity(toLogin)
            }

            else -> ""
        }
        return super.onOptionsItemSelected(item)
    }
}
