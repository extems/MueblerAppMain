package com.example.mueblerapp20.fragmentsLogin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.mueblerapp20.R
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

    lateinit var email: String
    lateinit var password: String
    lateinit var editMail: EditText
    lateinit var editPass: EditText
    var encontradoMail: Boolean = false
    var encontradoPass: Boolean = false

    lateinit var btnChangePassword: Button
    lateinit var btnLogin: Button
    lateinit var btnRegister: Button
    lateinit var btnLoginGuest: Button
    lateinit var btnLoginGoogle: ImageButton

    var usuario: MutableList<Usuario> = ArrayList<Usuario>()

    private val PREF_NAME = "myPreferences"
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

            val toScreen2 = PantallaLoginDirections.actionPantallaLoginToPantallaCambiarContrasena()
            v.findNavController().navigate(toScreen2)

        }

        btnRegister.setOnClickListener() {

            val toScreen3 = PantallaLoginDirections.actionPantallaLoginToPantallaEleccionUsuario(
                true,
                false
            )
            v.findNavController().navigate(toScreen3)

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
                                Snackbar.make(v, "Entro al for", Snackbar.LENGTH_SHORT)
                            }
                            if (usuario.isNotEmpty()) {
                                if (usuario[0].contrasena == password) {
                                    val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                                    val editor = sharedPref.edit()
                                    editor.putString("USERID", usuario[0].email)
                                    editor.apply()
//                                  user = sharedPref.getString("USERID","default")!!
                                    Snackbar.make(
                                        v,
                                        "Jorge",
                                        Snackbar.LENGTH_SHORT
                                    )
                                        .show()
                                } else {
                                    Snackbar.make(v, "Contraseña incorrecta", Snackbar.LENGTH_SHORT)
                                        .show()
                                }
                            }
                            else{
                                Snackbar.make(
                                    v,
                                    "No se ha encontrado su usuario, registrese para acceder a la aplicacion",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Snackbar.make(v, "Datasnapshot es nulo", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Snackbar.make(
                            v,
                            "Ha ocurrido un error inesperado, intente nuevamente",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
            }
            else {
                Snackbar.make(v, "Rellene los campos", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}