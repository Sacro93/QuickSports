package com.example.quicksports.presentation.Navigation


sealed class Screen(val route: String) {
    object Start : Screen("start")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object CreateEvent : Screen("crear_evento")
    object CreateEvent2 : Screen("crear_evento_paso2")
    object YourEvents : Screen("tus_eventos")
    object EventZone : Screen("eventos_zona")
    object Friends : Screen("amistades")
    object Centers : Screen("centros")
    object Profile : Screen("perfil")
    object EditProfile : Screen("editar_perfil")
    object FriendSelector : Screen("friend_selector")
    object NotificationsScreen : Screen("notifications_screen")
    object FAQ: Screen("faq")
}
