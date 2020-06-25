package com.example.mueblerapp20.classes

class Carrito (listaDeMuebles : MutableList <Mueble>, precioTotal : Double, precioDeEnvioTotal : Double) {

    var listaDeMuebles : MutableList <Mueble>
    var precioTotal : Double
    var precioDeEnvioTotal : Double

    init {
        this.listaDeMuebles = listaDeMuebles
        this.precioTotal = precioTotal
        this.precioDeEnvioTotal = precioDeEnvioTotal
    }

}