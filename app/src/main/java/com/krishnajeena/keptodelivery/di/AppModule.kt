package com.krishnajeena.keptodelivery.di

import android.content.Context
import com.krishnajeena.keptodelivery.data.repository.AuthRepositoryImpl
import com.krishnajeena.keptodelivery.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context,)
    : AuthRepository{
        return AuthRepositoryImpl(context)
    }
}