package com.example.food_hub.ui.features.auth.login

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_hub.data.FoodApi
import com.example.food_hub.data.auth.GoogleAuthUIProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val foodApi: FoodApi) : ViewModel() {

    val googleAuthUIProvider = GoogleAuthUIProvider()

    private val _uiState = MutableStateFlow<LoginEvent>(LoginEvent.Nothing)
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<LoginNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            _navigationEvent.emit(LoginNavigationEvent.NavigateToHome)
        }
    }

    fun onSignUpClicked() {
        viewModelScope.launch {
            _navigationEvent.emit(LoginNavigationEvent.NavigateToSignUp)
        }
    }

    fun onGoogleSignInClicked(context: Context) {
        viewModelScope.launch {
            _uiState.value = LoginEvent.Loading
            val response = googleAuthUIProvider.signIn(context, CredentialManager.create(context))
            if (response != null) {
                _uiState.value = LoginEvent.Success
                _navigationEvent.emit(LoginNavigationEvent.NavigateToHome)
            } else {
                _uiState.value = LoginEvent.Error
            }
        }
    }

    sealed class LoginEvent {
        data object Nothing : LoginEvent()
        data object Success : LoginEvent()
        data object Error : LoginEvent()
        data object Loading : LoginEvent()
    }

    sealed class LoginNavigationEvent {
        data object NavigateToHome : LoginNavigationEvent()
        data object NavigateToSignUp : LoginNavigationEvent()
    }
}