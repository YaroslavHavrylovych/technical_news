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

package com.gmail.yaroslavlancelot.data.network.articles.providers

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.http.GET

interface TokarService {
    @GET("https://tokar.ua/tag/article/feed")
    suspend fun getTokarArticles(): TokarRss
}

@Root(name = "rss", strict = false)
class TokarRss(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: TokarChannel
)

@Root(name = "channel", strict = false)
class TokarChannel(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String,
    @field:ElementList(name = "item", inline = true)
    @param:ElementList(name = "item", inline = true)
    val articles: List<TokarArticle>? = null
)

@Root(name = "item", strict = false)
class TokarArticle(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String,

    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String,

    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    val date: String
)
