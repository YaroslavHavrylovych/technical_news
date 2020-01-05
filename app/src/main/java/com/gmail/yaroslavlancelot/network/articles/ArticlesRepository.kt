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

