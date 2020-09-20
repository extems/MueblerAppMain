package com.example.mueblerapp20.classes

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class Guest (
    idguest: String?,
    permiso: String?,
    carrito: MutableList<String>,
    favoritos: MutableList<String>

    ) : Parcelable {
    var idguest: String

    var permiso: String

    var carrito: MutableList<String>

    var favoritos: MutableList<String>

    init {
        this.idguest = idguest!!
        this.permiso = permiso!!
        this.carrito = carrito!!
        this.favoritos = favoritos!!
    }

    constructor(source: Parcel) : this(
    source.readString(),
    source.readString(),
    source.createStringArrayList()!!,
    source.createStringArrayList()!!
    )

    constructor() : this ("","",ArrayList<String>(),ArrayList<String>())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(idguest)
        writeString(permiso)
        writeStringList(carrito)
        writeStringList(favoritos)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Guest> = object : Parcelable.Creator<Guest> {
            override fun createFromParcel(source: Parcel): Guest = Guest(source)
            override fun newArray(size: Int): Array<Guest?> = arrayOfNulls(size)
        }
    }
}