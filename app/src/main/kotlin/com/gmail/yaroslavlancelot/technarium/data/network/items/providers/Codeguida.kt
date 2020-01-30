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

package com.gmail.yaroslavlancelot.technarium.data.network.items.providers

import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.network.items.NetworkItem
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.http.GET

interface CodeguidaService {
    @GET("https://codeguida.com/feeds/posts/")
    suspend fun getArticles(): CodeguidaRss

    @GET("https://codeguida.com/feeds/news/")
    suspend fun getNews(): CodeguidaRss
}

@Root(name = "rss", strict = false)
class CodeguidaRss(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: CodeguidaChannel
)

@Root(name = "channel", strict = false)
class CodeguidaChannel(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String,

    @field:ElementList(name = "item", inline = true)
    @param:ElementList(name = "item", inline = true)
    val items: List<CodeguidaItem>? = null
)

@Root(name = "item", strict = false)
class CodeguidaItem(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String,

    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String,

    @field:Element(name = "description")
    @param:Element(name = "description")
    val description: String
) : NetworkItem {
    override fun title() = title

    override fun link() = link.replace("http://", "https://")

    //TODO change to date from the server
    override fun date() = "Thu, 03 Jan 2019 11:00:05 +0000"

    override fun provider() = ProviderType.CODEGUIDA

    override fun description() = description
}
