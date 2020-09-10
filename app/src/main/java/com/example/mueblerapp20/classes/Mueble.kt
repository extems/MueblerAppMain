package com.example.mueblerapp20.classes

import android.os.Parcel
import android.os.Parcelable

class Mueble(
    urlImagen: String?,
    nombre: String?,
    precio: Double,
    descripcion: String?,
    calificacion: Double,
    cantidadCalificacion: Int,
    categorias: String?,
    vendedor: String?,
    envio: Boolean,
    costoDeEnvio: Double) :

    Parcelable {

    var urlImagen: String

    var nombre: String

    var precio: Double

    var descripcion: String

    var calificacion: Double

    var cantidadCalificacion: Int

    var categorias: String

    var vendedor: String

    var envio: Boolean

    var costoDeEnvio: Double

    init {
        this.urlImagen = urlImagen!!
        this.nombre = nombre!!
        this.precio = precio!!
        this.descripcion = descripcion!!
        this.calificacion = calificacion!!
        this.cantidadCalificacion = cantidadCalificacion!!
        this.categorias = categorias!!
        this.vendedor = vendedor!!
        this.envio = envio!!
        this.costoDeEnvio = costoDeEnvio!!
        //   this.listaDeFotos = listaDeFotos
    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readDouble(),
        source.readString(),
        source.readDouble(),
        source.readInt(),
        source.readString(),
        source.readString(),
        1 == source.readInt(),
        source.readDouble()
    )

    constructor() : this("","",0.00,"",0.00,0,"","",false,0.00)

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(urlImagen)
        writeString(nombre)
        writeDouble(precio)
        writeString(descripcion)
        writeDouble(calificacion)
        writeInt(cantidadCalificacion)
        writeString(categorias)
        writeString(vendedor)
        writeInt((if (envio) 1 else 0))
        writeDouble(costoDeEnvio)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Mueble> = object : Parcelable.Creator<Mueble> {
            override fun createFromParcel(source: Parcel): Mueble = Mueble(source)
            override fun newArray(size: Int): Array<Mueble?> = arrayOfNulls(size)
        }
    }
}