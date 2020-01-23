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

package com.gmail.yaroslavlancelot.technarium.tests.network

import com.gmail.yaroslavlancelot.technarium.data.ProviderType.CODEGUIDA
import com.gmail.yaroslavlancelot.technarium.data.ProviderType.TOKAR
import com.gmail.yaroslavlancelot.technarium.helpers.di.DaggerTestApplicationComponent
import com.gmail.yaroslavlancelot.technarium.helpers.network.FakeCodeguida
import com.gmail.yaroslavlancelot.technarium.data.DataRepository
import com.gmail.yaroslavlancelot.technarium.data.Item
import com.gmail.yaroslavlancelot.technarium.data.network.NetworkModule
import com.gmail.yaroslavlancelot.technarium.helpers.network.FakeTokar
import com.gmail.yaroslavlancelot.technarium.tests.BaseTest
import io.mockk.every
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class NetworkModuleTest : BaseTest() {
    private val codeguidaArticlesAmount = 6
    private val tokarArticlesAmount = 7
    @Inject
    @JvmField
    var dataRepository: DataRepository? = null

    @Before
    fun setUp() {
        val networkModule = spyk(NetworkModule())
        every { networkModule.provideCodeguidaApi(any()) } returns (FakeCodeguida(codeguidaArticlesAmount))
        every { networkModule.provideTokarApi(any()) } returns (FakeTokar(tokarArticlesAmount))
        val dagger = DaggerTestApplicationComponent.builder()
            .networkModule(networkModule)
            .build()
        dagger.inject(this)
    }

    @Test
    fun `Validates codeguida`() {
        assertNotNull("codeguida is not init by dagger", dataRepository)
        var items: List<Item>? = null
        runBlocking {
            items = dataRepository?.loadArticles(setOf(CODEGUIDA))
        }
        assertNotNull("articles not loaded", items)
        assert(items?.size == codeguidaArticlesAmount) { "wrong articles amount" }
    }

    @Test
    fun `Validates tokar`() {
        assertNotNull("codeguida is not init by dagger", dataRepository)
        var items: List<Item>? = null
        runBlocking {
            items = dataRepository?.loadArticles(setOf(TOKAR))
        }
        assertNotNull("articles not loaded", items)
        assert(items?.size == tokarArticlesAmount) { "wrong articles amount" }
    }

    @Test
    fun `Validates providers collaboration`() {
        assertNotNull("codeguida is not init by dagger", dataRepository)
        var items: List<Item>? = null
        runBlocking {
            items = dataRepository?.loadArticles(setOf(CODEGUIDA, TOKAR))
        }
        assertNotNull("articles not loaded", items)
        assert(items?.size == (codeguidaArticlesAmount + tokarArticlesAmount)) { "wrong articles amount" }
    }
}