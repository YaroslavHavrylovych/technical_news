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

interface PingvinService {
    @GET("https://pingvin.pro/category/gadgets/reviews-gadgets/feed")
    suspend fun getReviews(): PingvinRss

    @GET("https://pingvin.pro/category/gadgets/article-gadget/feed")
    suspend fun getArticles(): PingvinRss

    @GET("https://pingvin.pro/category/blogy/feed")
    suspend fun getBlogs(): PingvinRss

    @GET("https://pingvin.pro/category/gadgets/news-gadgets/feed")
    suspend fun getNews(): PingvinRss
}

@Root(name = "rss", strict = false)
data class PingvinRss(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: PingvinChannel
)

@Root(name = "channel", strict = false)
data class PingvinChannel(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String,
    @field:ElementList(name = "item", inline = true, required = false)
    @param:ElementList(name = "item", inline = true, required = false)
    val items: List<PingvinItem>? = null
)

@Root(name = "item", strict = false)
class PingvinItem(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String,

    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String,

    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    val date: String,

    @field:Element(name = "description")
    @param:Element(name = "description")
    val description: String
) : NetworkItem {
    override fun title() = title

    override fun link() = link

    override fun date() = date

    override fun provider() = ProviderType.PINGVIN

    override fun description() = description
}
