package com.santiagolozada.loginbaubap.login.presentation.di

import com.santiagolozada.loginbaubap.login.data.LoginDataSource
import com.santiagolozada.loginbaubap.login.data.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class PresentationModule {

    @Provides
    @ViewModelScoped
    fun provideLoginRepository(dataSource: LoginDataSource) = LoginRepository(dataSource)
}