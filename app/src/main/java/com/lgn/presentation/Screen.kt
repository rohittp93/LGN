package com.lgn.presentation

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object HomeScreen: Screen("home_screen")
    object AllEventsScreen: Screen("all_events")
    object VenueScreen: Screen("venue_screen")
    object ProfileScreen: Screen("profile_screen")
    object EventDetailScreen: Screen("event_detail_screen")
    object EditVenueScreen: Screen("edit_venue_screen")
    object AllTablesScreen: Screen("all_tables_screen")
    object EventSelectionScreen: Screen("event_selection_screen")
    object ScannerScreen: Screen("scanner_screen")
    object EventSummaryScreen: Screen("event_summary_screen")
    object AddTableScreen: Screen("add_table_screen")
}