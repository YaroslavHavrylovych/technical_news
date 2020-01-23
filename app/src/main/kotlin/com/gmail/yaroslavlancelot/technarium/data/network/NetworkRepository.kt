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

package com.gmail.yaroslavlancelot.technarium.data.network

import androidx.annotation.WorkerThread
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.network.items.NetworkItem
import com.gmail.yaroslavlancelot.technarium.data.network.items.providers.CodeguidaService
import com.gmail.yaroslavlancelot.technarium.data.network.items.providers.DouService
import com.gmail.yaroslavlancelot.technarium.data.network.items.providers.TokarService

interface NetworkRepository {
    @WorkerThread
    suspend fun refreshArticles(providers: Set<ProviderType>): List<NetworkItem>

    @WorkerThread
    suspend fun refreshNews(providers: Set<ProviderType>): List<NetworkItem>

    @WorkerThread
    suspend fun refreshOpenings(providers: Set<ProviderType>, filter: Map<String, String>): List<NetworkItem>

    @WorkerThread
    suspend fun refreshEvents(providers: Set<ProviderType>): List<NetworkItem>
}

class NetworkRepositoryImpl(
    private val codeguidaService: CodeguidaService,
    private val tokarService: TokarService,
    private val douService: DouService
) : NetworkRepository {
    override suspend fun refreshArticles(providers: Set<ProviderType>): List<NetworkItem> {
        val res = ArrayList<NetworkItem>()
        if (providers.contains(ProviderType.CODEGUIDA)) codeguidaService.getArticles().channel.items?.forEach {
            res.add(it)
        }
        if (providers.contains(ProviderType.TOKAR)) tokarService.getArticles().channel.items?.forEach {
            res.add(it)
        }
        if (providers.contains(ProviderType.DOU)) {
            douService.getArticles().channel.items?.forEach { res.add(it) }
            douService.getColumns().channel.items?.forEach { res.add(it) }
            douService.getInterviews().channel.items?.forEach { res.add(it) }
        }
        return res
    }

    override suspend fun refreshNews(providers: Set<ProviderType>): List<NetworkItem> {
        val res = ArrayList<NetworkItem>()
        if (providers.contains(ProviderType.TOKAR)) tokarService.getNews().channel.items?.forEach {
            res.add(it)
        }
        if (providers.contains(ProviderType.DOU)) douService.getNews().channel.items?.forEach {
            res.add(it)
        }
        if (providers.contains(ProviderType.CODEGUIDA)) codeguidaService.getNews().channel.items?.forEach {
            res.add(it)
        }
        return res
    }

    override suspend fun refreshOpenings(providers: Set<ProviderType>, filter: Map<String, String>): List<NetworkItem> {
        val res = ArrayList<NetworkItem>()
        //TODO change filters here
        if (providers.contains(ProviderType.DOU)) douService.getOpenings(filter).channel.items?.forEach {
            res.add(it)
        }
        return res
    }

    override suspend fun refreshEvents(providers: Set<ProviderType>): List<NetworkItem> {
        val res = ArrayList<NetworkItem>()
        if (providers.contains(ProviderType.DOU)) douService.getEvents().channel.items?.forEach {
            res.add(it)
        }
        return res
    }
}