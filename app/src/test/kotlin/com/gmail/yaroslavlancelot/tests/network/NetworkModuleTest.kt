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

package com.gmail.yaroslavlancelot.tests.network

import com.gmail.yaroslavlancelot.helpers.di.DaggerTestApplicationComponent
import com.gmail.yaroslavlancelot.helpers.network.FakeCodeguida
import com.gmail.yaroslavlancelot.network.articles.ArticlesRepository
import com.gmail.yaroslavlancelot.network.articles.IArticle
import com.gmail.yaroslavlancelot.network.articles.NetworkModule
import com.gmail.yaroslavlancelot.tests.BaseTest
import io.mockk.every
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class NetworkModuleTest : BaseTest() {
    private val articlesAmount = 5
    @Inject
    @JvmField
    var articlesRepository: ArticlesRepository? = null

    @Before
    fun setUp() {
        val networkModule = spyk(NetworkModule())
        every { networkModule.provideCodeguidaApi(any()) } returns (FakeCodeguida(articlesAmount))
        val dagger = DaggerTestApplicationComponent.builder()
            .networkModule(networkModule)
            .build()
        dagger.inject(this)
    }

    @Test
    fun `Validates codeguida`() {
        assertNotNull("codeguida is not init by dagger", articlesRepository)
        var articles: List<IArticle>? = null
        runBlocking {
            articles = articlesRepository?.loadArticles()
        }
        assertNotNull("articles not loaded", articles)
        assert(articles?.size == articlesAmount) { "wrong articles amount" }
    }
}