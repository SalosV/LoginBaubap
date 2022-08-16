package com.santiagolozada.loginbaubap.login.data.di

import com.santiagolozada.loginbaubap.login.data.LoginDataSource
import com.santiagolozada.loginbaubap.login.data.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideLoginDataSources() = LoginDataSource()
}