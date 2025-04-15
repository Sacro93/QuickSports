package com.example.quicksports.data.defaulData

import com.example.quicksports.R
import com.example.quicksports.data.models.Friend

object DefaultFriends {
    fun get(): List<Friend> = listOf(
        Friend("Juan Pérez", "123456789", R.drawable.hombre, 28, listOf(1, 3, 4)),
        Friend("María Gómez", "987654321", R.drawable.mujer_2, 26, listOf(2, 1)),
        Friend("Carlos López", "456789123", R.drawable.hombre, 30, listOf(4, 2, 1, 3)),
        Friend("Ana Fernández", "321654987", R.drawable.mujer_2, 24, listOf(2, 1)),
        Friend("Lucas Torres", "654789321", R.drawable.hombre, 27, listOf(3, 4, 2)),
        Friend("Lucía Ramírez", "159753468", R.drawable.mujer_2, 25, listOf(3, 2, 4, 1)),
        Friend("Pedro Sánchez", "789456123", R.drawable.hombre, 29, listOf(3, 4)),
        Friend("Sofía Martínez", "951753852", R.drawable.mujer_2, 23, listOf(2, 1, 4, 3)),
        Friend("Julián Castro", "357159486", R.drawable.hombre, 31, listOf(1, 3, 2)),
        Friend("Valentina Ruiz", "123789456", R.drawable.mujer_2, 22, listOf(2, 1)),
        Friend("Diego Ríos", "147258369", R.drawable.hombre, 32, listOf(3, 4, 2, 1)),
        Friend("Marta Silva", "369258147", R.drawable.mujer_2, 28, listOf(4, 1, 3, 2)),
        Friend("Facundo Aguirre", "741852963", R.drawable.hombre, 30, listOf(3)),
        Friend("Paula Ortega", "852963741", R.drawable.mujer_2, 27, listOf(4, 2, 3)),
        Friend("Ezequiel Méndez", "963741852", R.drawable.hombre, 29, listOf(4, 3)),
        Friend("Florencia Díaz", "159486357", R.drawable.mujer_2, 25, listOf(1)),
        Friend("Nahuel Castro", "951357456", R.drawable.hombre, 31, listOf(3, 4)),
        Friend("Camila Romero", "753159456", R.drawable.mujer_2, 26, listOf(4, 2)),
        Friend("Tomás Blanco", "654951357", R.drawable.hombre, 28, listOf(2, 4, 1)),
        Friend("Ailén Navarro", "357654159", R.drawable.mujer_2, 24, listOf(4)),
        Friend("Agustín Vega", "321789654", R.drawable.hombre, 29, listOf(1, 2, 3, 4)),
        Friend("Julieta Molina", "147369258", R.drawable.mujer_2, 27, listOf(4, 1, 2, 3)),
        Friend("Matías Herrera", "258741369", R.drawable.hombre, 30, listOf(1, 3, 2)),
        Friend("Rocío Suárez", "369147258", R.drawable.mujer_2, 26, listOf(2, 4)),
        Friend("Federico Campos", "951654753", R.drawable.hombre, 32, listOf(3, 2, 4)),
        Friend("Milagros Benítez", "753456159", R.drawable.mujer_2, 25, listOf(1)),
        Friend("Bruno Escobar", "159357456", R.drawable.hombre, 28, listOf(4, 2, 3, 1)),
        Friend("Cecilia Ibáñez", "357951654", R.drawable.mujer_2, 24, listOf(4)),
        Friend("Gonzalo Ortega", "456789654", R.drawable.hombre, 31, listOf(2, 4, 1, 3)),
        Friend("Tamara Paredes", "789123456", R.drawable.mujer_2, 22, listOf(4, 2, 1, 3))
    )
}

