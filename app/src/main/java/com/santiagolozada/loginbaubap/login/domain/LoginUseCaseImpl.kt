package com.santiagolozada.loginbaubap.login.domain

import com.santiagolozada.loginbaubap.R
import com.santiagolozada.loginbaubap.login.data.LoginRepository
import com.santiagolozada.loginbaubap.login.data.Result
import com.santiagolozada.loginbaubap.login.presentation.LoggedInUserView
import com.santiagolozada.loginbaubap.login.presentation.LoginResult
import com.santiagolozada.loginbaubap.login.presentation.viewModels.DURATION_REQUEST
import javax.inject.Inject
import kotlinx.coroutines.delay

class LoginUseCaseImpl @Inject constructor(private val loginRepository: LoginRepository) : LoginUseCase {

    override suspend fun invoke(username: String, password: String): LoginResult {
        val result = loginRepository.login(username, password)

        //Simulates the time in which the service responds
        delay(DURATION_REQUEST)

        return if (result is Result.Success) {
            LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            LoginResult(error = R.string.login_failed)
        }
    }
}