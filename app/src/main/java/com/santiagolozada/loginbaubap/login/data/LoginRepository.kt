package com.santiagolozada.loginbaubap.login.data

import com.santiagolozada.loginbaubap.login.data.model.LoggedInUser

class LoginRepository(private val dataSource: LoginDataSource) {

    fun login(username: String, password: String): Result<LoggedInUser> {
        return dataSource.login(username, password)
    }

}