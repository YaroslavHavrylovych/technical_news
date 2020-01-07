/**
 * Copyright (C) 2020 Yaroslav Havrylovych Open Source Project
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

package com.gmail.yaroslavlancelot.network.articles

import com.gmail.yaroslavlancelot.network.articles.services.CodeguidaService

interface ArticlesRepository {
    suspend fun loadArticles(): List<IArticle>
}

internal class ArticlesRepositoryImpl(
    private val codeguidaService: CodeguidaService
) : ArticlesRepository {
    override suspend fun loadArticles(): List<IArticle> {
        val res = ArrayList<IArticle>()
        codeguidaService
            .getCodeguidaArticles().channel.articles?.forEach { res.add(CodeguidaArticle(it)) }
        return res
    }
}

