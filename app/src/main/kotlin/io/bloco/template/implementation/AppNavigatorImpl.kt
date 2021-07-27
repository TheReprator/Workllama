package io.bloco.template.implementation

import androidx.navigation.NavController
import app.template.navigation.AppNavigator
import dagger.hilt.android.scopes.ActivityScoped
import io.bloco.contactlist.ui.ContactListDirections
import javax.inject.Inject

@ActivityScoped
class AppNavigatorImpl @Inject constructor() : AppNavigator {

    override fun navigateToContactDetailScreen(navController: NavController, id: String) {
        val direction = ContactListDirections.navigationContactListToNavigationContactDetail(id)
        navController.navigate(direction)
    }

    override fun navigateToBack(navController: NavController) {
        navController.navigateUp()
    }
}