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

package com.gmail.yaroslavlancelot.data.network.articles

import com.gmail.yaroslavlancelot.data.ProviderType
import com.gmail.yaroslavlancelot.data.ProviderType.CODEGUIDA
import com.gmail.yaroslavlancelot.data.ProviderType.TOKAR
import com.gmail.yaroslavlancelot.data.network.articles.providers.CodeguidaService
import com.gmail.yaroslavlancelot.data.network.articles.providers.TokarService

interface ArticlesRepository {
    suspend fun loadArticles(providers: Set<ProviderType>): List<IArticle>
}

internal class ArticlesRepositoryImpl(
    private val codeguidaService: CodeguidaService,
    private val tokarService: TokarService
) : ArticlesRepository {
    override suspend fun loadArticles(providers: Set<ProviderType>): List<IArticle> {
        val res = ArrayList<IArticle>()
        if (providers.contains(CODEGUIDA))
            codeguidaService
                .getCodeguidaArticles().channel.articles?.forEach { res.add(CodeguidaArticleImpl(it)) }
        if (providers.contains(TOKAR))
            tokarService
                .getTokarArticles().channel.articles?.forEach { res.add(TokarArticleImpl(it)) }
        res.sortByDescending { it.getPublicationDate().time }
        return res
    }
}

