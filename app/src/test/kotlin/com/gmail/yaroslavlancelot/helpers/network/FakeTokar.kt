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

import com.gmail.yaroslavlancelot.data.network.items.providers.TokarItem
import com.gmail.yaroslavlancelot.data.network.items.providers.TokarChannel
import com.gmail.yaroslavlancelot.data.network.items.providers.TokarRss
import com.gmail.yaroslavlancelot.data.network.items.providers.TokarService
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FakeTokar(private val articlesAmount: Int) : TokarService {
    private val dateFormat = SimpleDateFormat(
        "EEE, dd MMM yyyy HH:mm:ss Z",
        Locale.getDefault()
    )

    override suspend fun getArticles(): TokarRss {
        val articles = ArrayList<TokarItem>()
        for (i in 1..articlesAmount) articles.add(TokarItem("Article$i", "ALink$i", dateFormat.format(Date())))
        val tokarChannel = TokarChannel("Codeguida test", articles)
        return TokarRss(tokarChannel)
    }

    override suspend fun getNews(): TokarRss {
        val articles = ArrayList<TokarItem>()
        for (i in 1..articlesAmount) articles.add(TokarItem("News$i", "NLink$i", dateFormat.format(Date())))
        val tokarChannel = TokarChannel("Codeguida test", articles)
        return TokarRss(tokarChannel)
    }
}