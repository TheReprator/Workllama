package app.template.navigation

import androidx.navigation.NavController

interface AppNavigator : ContactListNavigator, ContactDetailNavigator


interface BackNavigator {

    /**
     * @param navController The navcontroller is provided by respective activity or fragment
     */
    fun navigateToBack(navController: NavController)
}


interface ContactListNavigator  {
    fun navigateToContactDetailScreen(
        navController: NavController, id: String)
}

interface ContactDetailNavigator: BackNavigator