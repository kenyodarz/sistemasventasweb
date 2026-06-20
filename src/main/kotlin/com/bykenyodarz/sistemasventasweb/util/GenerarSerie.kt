package com.bykenyodarz.sistemasventasweb.util

/**
 * Clase utilitaria para la generación secuencial y formateo de números de serie
 * para facturas u otros documentos de venta.
 */
class GenerarSerie {

    var dato: Int? = null

    lateinit var numero: String

    /**
     * Incrementa el número entero base recibido en 1 y lo formatea como una cadena
     * rellenada con ceros a la izquierda hasta completar una longitud de 8 dígitos.
     * El resultado se almacena en la propiedad [numero].
     *
     * @param dato El número entero base de la serie (usualmente el número de serie de la última venta).
     */
    fun numeroSerie(dato: Int) {
        this.dato = dato + 1
        this.numero = ""
        when {
            (this.dato!! >= 10000000) && (this.dato!! <= 100000000) -> numero = "${this.dato}"
            (this.dato!! >= 1000000) && (this.dato!! <= 9999999) -> numero = "0${this.dato}"
            (this.dato!! >= 100000) && (this.dato!! <= 999999) -> numero = "00${this.dato}"
            (this.dato!! >= 10000) && (this.dato!! <= 99999) -> numero = "000${this.dato}"
            (this.dato!! >= 1000) && (this.dato!! <= 9999) -> numero = "0000${this.dato}"
            (this.dato!! >= 100) && (this.dato!! <= 999) -> numero = "00000${this.dato}"
            (this.dato!! >= 10) && (this.dato!! <= 99) -> numero = "000000${this.dato}"
            this.dato!! <= 9 -> numero = "0000000${this.dato}"
        }
        this.numero
    }

}
