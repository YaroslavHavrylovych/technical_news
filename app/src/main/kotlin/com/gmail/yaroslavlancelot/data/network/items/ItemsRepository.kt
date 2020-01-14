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

package com.gmail.yaroslavlancelot.data.network.items

import com.gmail.yaroslavlancelot.data.ProviderType
import com.gmail.yaroslavlancelot.data.ProviderType.CODEGUIDA
import com.gmail.yaroslavlancelot.data.ProviderType.TOKAR
import com.gmail.yaroslavlancelot.data.network.items.providers.CodeguidaService
import com.gmail.yaroslavlancelot.data.network.items.providers.TokarService

interface ItemsRepository {
    suspend fun loadArticles(providers: Set<ProviderType>): List<IItem>

    suspend fun loadNews(providers: Set<ProviderType>): List<IItem>
}

internal class ItemsRepositoryImpl(
    private val codeguidaService: CodeguidaService,
    private val tokarService: TokarService
) : ItemsRepository {
    override suspend fun loadArticles(providers: Set<ProviderType>): List<IItem> {
        val res = ArrayList<IItem>()
        if (providers.contains(CODEGUIDA)) {
            codeguidaService
                .getCodeguidaArticles().channel.items?.forEach { res.add(CodeguidaItemImpl(it)) }
        }
        if (providers.contains(TOKAR)) {
            tokarService
                .getTokarArticles().channel.items?.forEach { res.add(TokarItemImpl(it)) }
        }
        return res
    }

    override suspend fun loadNews(providers: Set<ProviderType>): List<IItem> {
        val res = ArrayList<IItem>()
        if (providers.contains(TOKAR)) {
            tokarService.getTokarNews().channel.items?.forEach { res.add(TokarItemImpl(it)) }
        }
        return res
    }
}

