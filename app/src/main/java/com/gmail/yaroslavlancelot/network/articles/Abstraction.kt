package com.gmail.yaroslavlancelot.network.articles

import com.gmail.yaroslavlancelot.network.articles.services.Article

interface IArticle {
    fun getTitle(): String
}

internal class CodeguidaArticle(private val article: Article) : IArticle {
    override fun getTitle(): String {
        return article.title
    }
}