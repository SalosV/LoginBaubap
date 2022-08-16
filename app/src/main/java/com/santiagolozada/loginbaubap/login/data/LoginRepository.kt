package com.santiagolozada.loginbaubap.login.data

import com.santiagolozada.loginbaubap.login.data.model.LoggedInUser

class LoginRepository(private val dataSource: LoginDataSource) {

    var user: LoggedInUser? = null
        private set

    fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}