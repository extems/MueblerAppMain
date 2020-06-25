package com.example.mueblerapp20.classes

class Mueble ( urlImagen : String, nombre : String, precio : Double, descripcion : String, calificacion : Double, cantidadCalificacion : Int, categorias : String, vendedor : String, envio : Boolean, costoDeEnvio : Double, listaDeFotos : MutableList <String>) {

    var urlImagen : String
    var nombre : String
    var precio : Double
    var descripcion : String
    var calificacion : Double
    var cantidadCalificacion : Int
    var categorias : String
    var vendedor : String
    var envio : Boolean
    var costoDeEnvio : Double
    var listaDeFotos : MutableList <String>
    //variable modelo 3d

    init {
        this.urlImagen = urlImagen
        this.nombre = nombre
        this.precio = precio
        this.descripcion = descripcion
        this.calificacion = calificacion
        this.cantidadCalificacion = cantidadCalificacion
        this.categorias = categorias
        this.vendedor = vendedor
        this.envio = envio
        this.costoDeEnvio = costoDeEnvio
        this.listaDeFotos = listaDeFotos
    }
}