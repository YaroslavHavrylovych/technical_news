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

package com.gmail.yaroslavlancelot.helpers.network

import com.gmail.yaroslavlancelot.data.network.articles.providers.TokarArticle
import com.gmail.yaroslavlancelot.data.network.articles.providers.TokarChannel
import com.gmail.yaroslavlancelot.data.network.articles.providers.TokarRss
import com.gmail.yaroslavlancelot.data.network.articles.providers.TokarService

class FakeTokar(private val articlesAmount: Int) : TokarService {

    override suspend fun getTokarArticles(): TokarRss {
        val articles = ArrayList<TokarArticle>()
        for (i in 1..articlesAmount) articles.add(TokarArticle("Article$i", "Link$i", "today"))
        val tokarChannel = TokarChannel("Codeguida test", articles)
        return TokarRss(tokarChannel)
    }
}