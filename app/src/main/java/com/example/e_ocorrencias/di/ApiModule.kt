package com.example.e_ocorrencias.di

import android.content.Context
import com.example.e_ocorrencias.data.remote.AuthService
import com.example.e_ocorrencias.data.remote.BatalhaoService
import com.example.e_ocorrencias.data.remote.CorpoGuardaService
import com.example.e_ocorrencias.data.remote.OcorrenciaService
import com.example.e_ocorrencias.data.remote.PoliciaService
import com.example.e_ocorrencias.data.remote.ViaturaService
import com.example.e_ocorrencias.ui.screens.login.AuthInterceptor
import com.example.e_ocorrencias.ui.screens.login.SessionManager
import com.example.e_ocorrencias.utils.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "http://10.0.2.2:3000/api/"

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(sessionManager: SessionManager): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionManager))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideOcorrenciaService(retrofit: Retrofit): OcorrenciaService {
        return retrofit.create(OcorrenciaService::class.java)
    }

    @Provides
    @Singleton
    fun provideBatalhaoService(retrofit: Retrofit): BatalhaoService {
        return retrofit.create(BatalhaoService::class.java)
    }

    @Provides
    @Singleton
    fun provideViaturaService(retrofit: Retrofit): ViaturaService {
        return retrofit.create(ViaturaService::class.java)
    }

    @Provides
    @Singleton
    fun providePoliciaService(retrofit: Retrofit): PoliciaService {
        return retrofit.create(PoliciaService::class.java)
    }

    @Provides
    @Singleton
    fun provideCorpoGuardaService(retrofit: Retrofit): CorpoGuardaService {
        return retrofit.create(CorpoGuardaService::class.java)
    }

    @Provides
    @Singleton
    fun provideErrorHandler(): ErrorHandler = ErrorHandler()
}