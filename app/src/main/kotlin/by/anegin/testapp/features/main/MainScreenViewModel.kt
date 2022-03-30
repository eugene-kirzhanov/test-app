package by.anegin.testapp.features.main

import androidx.lifecycle.ViewModel
import by.anegin.testapp.core.navigation.AppNavigator
import by.anegin.testapp.features.clients.presentation.ClientsNavDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {

    fun showClientsList() {
        appNavigator.navigateTo(ClientsNavDestinations.clientsList())
    }
}