package com.example.mueblerapp20.classes

class Usuario (foto : String, nombre : String, mail : String, contrasena : String, numeroTelefonico : Int, permiso : String, favoritos : MutableList <Mueble>, venta : MutableList <Mueble>, carrito: Carrito){

    var foto : String
    var nombre : String
    var mail : String
    var contrasena : String
    var numeroTelefonico : Int
    var permiso : String
    var venta : MutableList <Mueble>
    var carrito : Carrito
    var favoritos : MutableList <Mueble>

    init {
        this.foto = foto
        this.nombre = nombre
        this.mail = mail
        this.contrasena = contrasena
        this.numeroTelefonico = numeroTelefonico
        this.permiso = permiso
        this.venta = venta
        this.carrito = carrito
        this.favoritos = favoritos
    }
}