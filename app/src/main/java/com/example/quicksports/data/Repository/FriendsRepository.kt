package com.example.quicksports.data.Repository

import com.example.quicksports.R
import com.example.quicksports.data.models.Friend

class FriendsRepository {
    fun getFriends(): List<Friend> = listOf(
        Friend("Juan Pérez", "123456789", R.drawable.ic_launcher_foreground, 28),
        Friend("María Gómez", "987654321", R.drawable.ic_launcher_foreground, 26),
        Friend("Carlos López", "456789123", R.drawable.ic_launcher_foreground, 30),
        Friend("Ana Fernández", "321654987", R.drawable.ic_launcher_foreground, 24),
        Friend("Lucas Torres", "654789321", R.drawable.ic_launcher_foreground, 27),
        Friend("Lucía Ramírez", "159753468", R.drawable.ic_launcher_foreground, 25),
        Friend("Pedro Sánchez", "789456123", R.drawable.ic_launcher_foreground, 29),
        Friend("Sofía Martínez", "951753852", R.drawable.ic_launcher_foreground, 23),
        Friend("Julián Castro", "357159486", R.drawable.ic_launcher_foreground, 31),
        Friend("Valentina Ruiz", "123789456", R.drawable.ic_launcher_foreground, 22),
        Friend("Diego Ríos", "147258369", R.drawable.ic_launcher_foreground, 32),
        Friend("Marta Silva", "369258147", R.drawable.ic_launcher_foreground, 28),
        Friend("Facundo Aguirre", "741852963", R.drawable.ic_launcher_foreground, 30),
        Friend("Paula Ortega", "852963741", R.drawable.ic_launcher_foreground, 27),
        Friend("Ezequiel Méndez", "963741852", R.drawable.ic_launcher_foreground, 29),
        Friend("Florencia Díaz", "159486357", R.drawable.ic_launcher_foreground, 25),
        Friend("Nahuel Castro", "951357456", R.drawable.ic_launcher_foreground, 31),
        Friend("Camila Romero", "753159456", R.drawable.ic_launcher_foreground, 26),
        Friend("Tomás Blanco", "654951357", R.drawable.ic_launcher_foreground, 28),
        Friend("Ailén Navarro", "357654159", R.drawable.ic_launcher_foreground, 24),
        Friend("Agustín Vega", "321789654", R.drawable.ic_launcher_foreground, 29),
        Friend("Julieta Molina", "147369258", R.drawable.ic_launcher_foreground, 27),
        Friend("Matías Herrera", "258741369", R.drawable.ic_launcher_foreground, 30),
        Friend("Rocío Suárez", "369147258", R.drawable.ic_launcher_foreground, 26),
        Friend("Federico Campos", "951654753", R.drawable.ic_launcher_foreground, 32),
        Friend("Milagros Benítez", "753456159", R.drawable.ic_launcher_foreground, 25),
        Friend("Bruno Escobar", "159357456", R.drawable.ic_launcher_foreground, 28),
        Friend("Cecilia Ibáñez", "357951654", R.drawable.ic_launcher_foreground, 24),
        Friend("Gonzalo Ortega", "456789654", R.drawable.ic_launcher_foreground, 31),
        Friend("Tamara Paredes", "789123456", R.drawable.ic_launcher_foreground, 22)
    )
}