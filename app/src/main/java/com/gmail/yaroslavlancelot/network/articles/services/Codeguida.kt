package com.gmail.yaroslavlancelot.network.articles.services

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.http.GET
import retrofit2.http.Url

interface CodeguidaService {
    @GET("https://codeguida.com/feeds/")
    suspend fun getCodeguidaArticles(): CodeguidaRss
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
    val articles: List<Article>? = null
)

@Root(name = "item", strict = false)
class Article(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String
)
