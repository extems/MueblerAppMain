package com.example.mueblerapp20.fragmentsLogin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mueblerapp20.MainActivity
import com.example.mueblerapp20.R
import com.example.mueblerapp20.adapters.MuebleFavoritoListAdapter
import com.example.mueblerapp20.classes.Guest
import com.example.mueblerapp20.classes.Mueble
import com.example.mueblerapp20.classes.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass.
 */

class PantallaLogin : Fragment() {

    lateinit var v: View

    lateinit var id : String
    lateinit var email: String
    lateinit var password: String
    lateinit var editMail: EditText
    lateinit var editPass: EditText

    lateinit var btnChangePassword: Button
    lateinit var btnLogin: Button
    lateinit var btnRegister: Button
    lateinit var btnLoginGuest: Button
    lateinit var btnLoginGoogle: ImageButton

    var usuario: MutableList<Usuario> = ArrayList<Usuario>()
    var guest: MutableList<Guest> = ArrayList<Guest>()

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pantalla_login, container, false)

        editMail = v.findViewById(R.id.user_Text)
        editPass = v.findViewById(R.id.password_Text)

        btnChangePassword = v.findViewById(R.id.btn_change_password)
        btnLogin = v.findViewById(R.id.btn_login)
        btnRegister = v.findViewById(R.id.btn_register)
        btnLoginGuest = v.findViewById(R.id.btn_login_guest)
        btnLoginGoogle = v.findViewById(R.id.btn_login_google)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnChangePassword.setOnClickListener() {
            val toScreenCambiarContrasena = PantallaLoginDirections.actionPantallaLoginToPantallaCambiarContrasena()
            v.findNavController().navigate(toScreenCambiarContrasena)
        }

        btnRegister.setOnClickListener() {

            val toScreenEleccionUsuario = PantallaLoginDirections.actionPantallaLoginToPantallaEleccionUsuario()
            v.findNavController().navigate(toScreenEleccionUsuario)
        }

        btnLogin.setOnClickListener() {

            if (editMail.length() > 0 && editPass.length() > 0) {
                email = editMail.text.toString()
                password = editPass.text.toString()

                var docRefMail = db.collection("usuarios")
                docRefMail.whereEqualTo("email", email).get()
                    .addOnSuccessListener { dataSnapshot ->
                        if (dataSnapshot != null) {
                            var usuarioDataSnapshot = dataSnapshot.documents
                            for (snapshot in usuarioDataSnapshot) {
                                snapshot.toObject<Usuario>()?.let { usuario.add(it) }
                            }
                            if (usuario.isNotEmpty()) {
                                if (usuario[0].contrasena == password) {
                                    val sharedPref: SharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
                                    val editor = sharedPref.edit()
                                    editor.putString("USERID", usuario[0].email)
                                    editor.putString("Permiso", "Vendedor")
                                    editor.apply()
                                    val toMain = Intent(this.activity, MainActivity::class.java)
                                    startActivity(toMain)
                                } else {
                                    Snackbar.make(v, "Contraseña incorrecta", Snackbar.LENGTH_SHORT).show()
                                }
                            }
                            else{
                                Snackbar.make(v, "No se ha encontrado su usuario, registrese para acceder", Snackbar.LENGTH_SHORT).show()
                            }
                        } else {
                            Snackbar.make(v, "Ha ocurrido un error inesperado, intente nuevamente", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Snackbar.make(v, "Ha ocurrido un error inesperado, intente nuevamente", Snackbar.LENGTH_SHORT).show()
                    }
            }
            else {
                Snackbar.make(v, "Rellene los campos", Snackbar.LENGTH_SHORT).show()
            }
        }

        btnLoginGuest.setOnClickListener() {
            id = "guest"
            var docRefMail = db.collection("guest")
            docRefMail.whereEqualTo("idguest", id).get().addOnSuccessListener { dataSnapshot ->
                    if (dataSnapshot != null) {
                        var guestDataSnapshot = dataSnapshot.documents
                        for (snapshot in guestDataSnapshot) {
                            snapshot.toObject<Guest>()?.let { guest.add(it) }
                        }
                        if (guest.isNotEmpty()) {
                            val sharedPref: SharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
                            val editor = sharedPref.edit()
                            editor.putString("USERID", guest[0].idguest)
                            editor.putString("Permiso", "Invitado")
                            editor.apply()
                            val toMain = Intent(this.activity, MainActivity::class.java)
                            startActivity(toMain)
                        }
                        else{
                            Snackbar.make(v, "La variable guest esta vacia", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Snackbar.make(v, "Ha ocurrido un error inesperado, intente nuevamente", Snackbar.LENGTH_SHORT).show()
                }
        }
    }
}