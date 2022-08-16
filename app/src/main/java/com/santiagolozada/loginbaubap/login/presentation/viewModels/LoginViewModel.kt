package com.santiagolozada.loginbaubap.login.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.core.util.PatternsCompat
import com.santiagolozada.loginbaubap.R
import com.santiagolozada.loginbaubap.common.coroutines.MainDispatcher
import com.santiagolozada.loginbaubap.common.viewModel.ScopedViewModel
import com.santiagolozada.loginbaubap.login.data.LoginRepository
import com.santiagolozada.loginbaubap.login.data.Result
import com.santiagolozada.loginbaubap.login.presentation.LoggedInUserView
import com.santiagolozada.loginbaubap.login.presentation.LoginFormState
import com.santiagolozada.loginbaubap.login.presentation.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val MAX_NUMBER_PASSWORD = 5
const val DURATION_REQUEST = 3000L

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    @MainDispatcher uiDispatcher: CoroutineDispatcher,
) : ScopedViewModel(uiDispatcher) {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    fun login(username: String, password: String) {
        launch {
            _loadingState.value = true
            val result = loginRepository.login(username, password)

            //Simulates the time in which the service responds
            delay(DURATION_REQUEST)

            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
            _loadingState.value = false
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(username).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > MAX_NUMBER_PASSWORD
    }
}