package com.bykenyodarz.sistemasventasweb.util

class GenerarSerie {

    var dato: Int? = null

    lateinit var numero: String

    fun numeroSerie(dato: Int) {
        this.dato = dato + 1
        this.numero = ""
        when {
            (this.dato!! >= 1000000) && (this.dato!! <= 100000000) -> numero = "${this.dato}"
            (this.dato!! >= 100000) && (this.dato!! <= 9999999) -> numero = "0${this.dato}"
            (this.dato!! >= 10000) && (this.dato!! <= 999999) -> numero = "00${this.dato}"
            (this.dato!! >= 1000) && (this.dato!! <= 99999) -> numero = "000${this.dato}"
            (this.dato!! >= 1000) && (this.dato!! <= 9999) -> numero = "0000${this.dato}"
            (this.dato!! >= 100) && (this.dato!! <= 999) -> numero = "00000${this.dato}"
            (this.dato!! >= 10) && (this.dato!! <= 99) -> numero = "000000${this.dato}"
            this.dato!! <= 9 -> numero = "0000000${this.dato}"
        }
        this.numero
    }

}
