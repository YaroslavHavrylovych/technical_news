/**
 * Copyright (C) 2020 Yaroslav Havrylovych
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("deprecation")

package com.gmail.yaroslavlancelot.data.network.items

import com.gmail.yaroslavlancelot.data.network.items.providers.CodeguidaService
import com.gmail.yaroslavlancelot.data.network.items.providers.DouService
import com.gmail.yaroslavlancelot.data.network.items.providers.TokarService
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
    fun provideNetworkRepository(
        codeguida: CodeguidaService,
        tokar: TokarService,
        dou: DouService
    ): ItemsRepository {
        return ItemsRepositoryImpl(codeguida, tokar, dou)
    }

    @Provides
    @Singleton
    internal fun provideTokarApi(retrofit: Retrofit): TokarService {
        return retrofit.create(TokarService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideDouApi(retrofit: Retrofit): DouService {
        return retrofit.create(DouService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideCodeguidaApi(retrofit: Retrofit): CodeguidaService {
        return retrofit.create(CodeguidaService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .baseUrl("https://stuburl.com")
            .build()
    }

    @Provides
    @Singleton
    internal fun provideOkHttp(): OkHttpClient {
        return OkHttpClient()
    }
}