package com.gmail.yaroslavlancelot.network.articles

import com.gmail.yaroslavlancelot.network.articles.services.CodeguidaService
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object ArticlesFactory {
    suspend fun loadArticles(): List<IArticle> {
        val res = ArrayList<IArticle>()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://codeguida.com/feeds/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        val service = retrofit.create(CodeguidaService::class.java)
        service.getCodeguidaArticles().channel.articles?.forEach { res.add(CodeguidaArticle(it)) }
        return res
    }
}

