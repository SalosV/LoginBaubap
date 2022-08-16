package com.santiagolozada.loginbaubap.login.domain

import com.santiagolozada.loginbaubap.login.presentation.LoginResult

interface LoginUseCase {
    suspend operator fun invoke(username: String, password: String): LoginResult
}