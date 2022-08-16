package com.santiagolozada.loginbaubap.login.presentation.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.santiagolozada.loginbaubap.MainCoroutineRule
import com.santiagolozada.loginbaubap.R
import com.santiagolozada.loginbaubap.getOrAwaitValue
import com.santiagolozada.loginbaubap.login.data.LoginRepository
import com.santiagolozada.loginbaubap.login.presentation.LoginFormState
import com.santiagolozada.loginbaubap.login.presentation.LoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val loginRepository = mock<LoginRepository>()

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(loginRepository, Dispatchers.Unconfined)
    }

    @Test
    fun `Is userName and password valid`() {
        val loginFormState = LoginFormState(isDataValid = true)

        loginViewModel.loginDataChanged("santiago@lozada.com", "123456")

        val value = loginViewModel.loginFormState.getOrAwaitValue()

        assert(loginFormState.isDataValid) { value.isDataValid }
    }

    @Test
    fun `Is userName invalid`() {
        val loginFormState = LoginFormState(usernameError = R.string.invalid_username)

        loginViewModel.loginDataChanged("santiago", "1234567")

        val value = loginViewModel.loginFormState.getOrAwaitValue()

        assertEquals(loginFormState.usernameError, value.usernameError)
    }

    @Test
    fun `Is password invalid`() {
        val loginFormState = LoginFormState(passwordError = R.string.invalid_password)

        loginViewModel.loginDataChanged("santiago@lozada.com", "123")

        val value = loginViewModel.loginFormState.getOrAwaitValue()

        assertEquals(loginFormState.passwordError, value.passwordError)
    }


    @Test
    fun `Login failed`() {
        val loginResult = LoginResult(error = R.string.login_failed)

        loginViewModel.login("santiago@lozada.com", "123456")

        val value = loginViewModel.loginResult.getOrAwaitValue()

        assertEquals(loginResult.error != null, value.error != null)
    }

}
