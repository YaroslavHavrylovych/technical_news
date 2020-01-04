package com.gmail.yaroslavlancelot.network.articles

import com.gmail.yaroslavlancelot.network.articles.services.CodeguidaService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkRepository(codeguida: CodeguidaService): ArticlesRepository {
        return ArticlesRepositoryImpl(codeguida)
    }

    @Provides
    @Singleton
    internal fun provideCodeguidaApi(retrofit: Retrofit): CodeguidaService {
        return retrofit.create(CodeguidaService::class.java)
    }

    @Provides
    internal fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .baseUrl("https://stuburl.com")
            .build()
    }

    @Provides
    internal fun provideOkHttp(): OkHttpClient {
        return OkHttpClient()
    }
}