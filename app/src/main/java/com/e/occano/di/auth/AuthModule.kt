package com.e.occano.di.auth

import android.content.SharedPreferences
import com.e.occano.api.auth.ApiAuthService
import com.e.occano.persistence.AccountPropertiesDao
import com.e.occano.persistence.AuthTokenDao
import com.e.occano.repository.auth.AuthRepository
import com.e.occano.repository.auth.AuthRepositoryImpl
import com.e.occano.session.SessionManager
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.FlowPreview
import retrofit2.Retrofit

@FlowPreview
@Module
object AuthModule{

    @JvmStatic
    @AuthScope
    @Provides
    fun provideOpenApiAuthService(retrofitBuilder: Retrofit.Builder): ApiAuthService {
        return retrofitBuilder
            .build()
            .create(ApiAuthService::class.java)
    }

    @JvmStatic
    @AuthScope
    @Provides
    fun provideAuthRepository(
        sessionManager: SessionManager,
        authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPropertiesDao,
        openApiAuthService: ApiAuthService,
        preferences: SharedPreferences,
        editor: SharedPreferences.Editor
    ): AuthRepository {
        return AuthRepositoryImpl(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager,
            preferences,
            editor
        )
    }


}