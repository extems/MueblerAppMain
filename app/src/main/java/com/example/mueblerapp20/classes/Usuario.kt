package com.example.mueblerapp20.classes

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class Usuario(
    id: String?,
    foto: String?,
    nombre: String?,
    email: String?,
    contrasena: String?,
    numeroTelefonico: Int,
    permiso: String?,
    venta: MutableList<String>,
    carrito: MutableList<String>,
    favoritos: MutableList<String>
) : Parcelable {
    var id: String

    var foto: String

    var nombre: String

    var email: String

    var contrasena: String

    var numeroTelefonico: Int

    var permiso: String

    var venta: MutableList<String>

    var carrito: MutableList<String>

    var favoritos: MutableList<String>

    init {
        this.id = id!!
        this.foto = foto!!
        this.nombre = nombre!!
        this.email = email!!
        this.contrasena = contrasena!!
        this.numeroTelefonico = numeroTelefonico
        this.permiso = permiso!!
        this.venta = venta!!
        this.carrito = carrito!!
        this.favoritos = favoritos!!
    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.createStringArrayList()!!,
        source.createStringArrayList()!!,
        source.createStringArrayList()!!
    )

    constructor() : this ("","","","","",0,"", ArrayList<String>(),ArrayList<String>(),ArrayList<String>())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(foto)
        writeString(nombre)
        writeString(email)
        writeString(contrasena)
        writeInt(numeroTelefonico)
        writeString(permiso)
        writeStringList(venta)
        writeStringList(carrito)
        writeStringList(favoritos)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Usuario> = object : Parcelable.Creator<Usuario> {
            override fun createFromParcel(source: Parcel): Usuario = Usuario(source)
            override fun newArray(size: Int): Array<Usuario?> = arrayOfNulls(size)
        }
    }
}