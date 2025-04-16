package com.example.quicksports.data.defaulData

import com.example.quicksports.R
import com.example.quicksports.data.models.Friend

object DefaultFriends {
    fun get(): List<Friend> = listOf(
        Friend("Juan Pérez", "123456789", R.drawable.hombre, 28, listOf("Tenis", "Pádel", "Fútbol")),
        Friend("María Gómez", "987654321", R.drawable.mujer_2, 26, listOf("Tenis")),
        Friend("Carlos López", "456789123", R.drawable.hombre, 30, listOf("Básquet", "Pádel", "Tenis")),
        Friend("Ana Fernández", "321654987", R.drawable.mujer_2, 24, listOf("Fútbol", "Básquet", "Pádel", "Tenis")),
        Friend("Lucas Torres", "654789321", R.drawable.hombre, 27, listOf("Pádel", "Tenis", "Básquet")),
        Friend("Lucía Ramírez", "159753468", R.drawable.mujer_2, 25, listOf("Tenis", "Pádel", "Fútbol")),
        Friend("Pedro Sánchez", "789456123", R.drawable.hombre, 29, listOf("Básquet", "Pádel", "Tenis", "Fútbol")),
        Friend("Sofía Martínez", "951753852", R.drawable.mujer_2, 23, listOf("Fútbol", "Básquet", "Tenis", "Pádel")),
        Friend("Julián Castro", "357159486", R.drawable.hombre, 31, listOf("Pádel", "Fútbol", "Básquet", "Tenis")),
        Friend("Valentina Ruiz", "123789456", R.drawable.mujer_2, 22, listOf("Básquet", "Tenis", "Fútbol", "Pádel")),
        Friend("Diego Ríos", "147258369", R.drawable.hombre, 32, listOf("Tenis", "Básquet")),
        Friend("Marta Silva", "369258147", R.drawable.mujer_2, 28, listOf("Básquet", "Tenis", "Fútbol")),
        Friend("Facundo Aguirre", "741852963", R.drawable.hombre, 30, listOf("Básquet")),
        Friend("Paula Ortega", "852963741", R.drawable.mujer_2, 27, listOf("Fútbol")),
        Friend("Ezequiel Méndez", "963741852", R.drawable.hombre, 29, listOf("Básquet")),
        Friend("Florencia Díaz", "159486357", R.drawable.mujer_2, 25, listOf("Fútbol", "Básquet", "Pádel")),
        Friend("Nahuel Castro", "951357456", R.drawable.hombre, 31, listOf("Pádel", "Tenis", "Básquet", "Fútbol")),
        Friend("Camila Romero", "753159456", R.drawable.mujer_2, 26, listOf("Pádel", "Básquet", "Tenis")),
        Friend("Tomás Blanco", "654951357", R.drawable.hombre, 28, listOf("Básquet", "Fútbol")),
        Friend("Ailén Navarro", "357654159", R.drawable.mujer_2, 24, listOf("Fútbol", "Pádel", "Básquet")),
        Friend("Agustín Vega", "321789654", R.drawable.hombre, 29, listOf("Tenis", "Pádel", "Fútbol", "Básquet")),
        Friend("Julieta Molina", "147369258", R.drawable.mujer_2, 27, listOf("Pádel")),
        Friend("Matías Herrera", "258741369", R.drawable.hombre, 30, listOf("Fútbol", "Pádel", "Tenis", "Básquet")),
        Friend("Rocío Suárez", "369147258", R.drawable.mujer_2, 26, listOf("Fútbol", "Pádel")),
        Friend("Federico Campos", "951654753", R.drawable.hombre, 32, listOf("Pádel", "Fútbol")),
        Friend("Milagros Benítez", "753456159", R.drawable.mujer_2, 25, listOf("Pádel", "Tenis", "Básquet", "Fútbol")),
        Friend("Bruno Escobar", "159357456", R.drawable.hombre, 28, listOf("Pádel", "Tenis", "Fútbol")),
        Friend("Cecilia Ibáñez", "357951654", R.drawable.mujer_2, 24, listOf("Pádel", "Tenis", "Fútbol")),
        Friend("Gonzalo Ortega", "456789654", R.drawable.hombre, 31, listOf("Básquet", "Tenis", "Pádel", "Fútbol")),
        Friend("Tamara Paredes", "789123456", R.drawable.mujer_2, 22, listOf("Básquet", "Tenis", "Fútbol"))
    )
}
