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
import retrofit2.http.QueryMap
import retrofit2.http.QueryName

interface DouService {
    @GET("https://dou.ua/lenta/articles/feed/")
    suspend fun getArticles(): DouRss

    @GET("https://dou.ua/lenta/interviews/feed/")
    suspend fun getInterviews(): DouRss

    @GET("https://dou.ua/lenta/columns/feed/")
    suspend fun getColumns(): DouRss

    @GET("https://dou.ua/lenta/news/feed/")
    suspend fun getNews(): DouRss

    @GET("https://dou.ua/lenta/events/feed/")
    suspend fun getEvents(): DouRss

    @GET("https://dou.ua/calendar/feed/%D1%81%D0%B5%D0%BC%D0%B8%D0%BD%D0%B0%D1%80/")
    suspend fun getWorkshops(): DouRss

    @GET("https://dou.ua/calendar/feed/%D0%BA%D1%83%D1%80%D1%81%D1%8B/")
    suspend fun getCourses(): DouRss

    @GET("https://dou.ua/calendar/feed/%D0%BA%D0%BE%D0%BD%D1%84%D0%B5%D1%80%D0%B5%D0%BD%D1%86%D0%B8%D1%8F/")
    suspend fun getConferences(): DouRss

    @GET("https://dou.ua/calendar/feed/%D0%BC%D0%B8%D1%82%D0%B0%D0%BF/")
    suspend fun getMeetUps(): DouRss

    @GET("https://jobs.dou.ua/vacancies/feeds/")
    suspend fun getOpenings(@QueryMap(encoded = false) filtersMap: Map<String, String>, @QueryName vararg singleFilters: String): DouRss
}

@Root(name = "rss", strict = false)
data class DouRss(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: DouChannel
)

@Root(name = "channel", strict = false)
data class DouChannel(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String,
    @field:ElementList(name = "item", inline = true, required = false)
    @param:ElementList(name = "item", inline = true, required = false)
    val items: List<DouItem>? = null
)

@Root(name = "item", strict = false)
class DouItem(
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

    override fun provider() = ProviderType.DOU

    override fun description() = description
}
