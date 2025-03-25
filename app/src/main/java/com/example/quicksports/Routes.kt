package com.example.quicksports


sealed class Screen(val route: String) {
    object Start : Screen("start")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object CrearEvento : Screen("crear_evento")
    object CrearEventoPaso2 : Screen("crear_evento_paso2")
    object TusEventos : Screen("tus_eventos")
    object EventosZona : Screen("eventos_zona")
    object Amistades : Screen("amistades")
    object Centros : Screen("centros")
    object Perfil : Screen("perfil")
    object EditarPerfil : Screen("editar_perfil")
    object FriendSelector : Screen("friend_selector")

}
