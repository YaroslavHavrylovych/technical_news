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

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.gmail.yaroslavlancelot.technarium.helpers.di.DaggerTestApplicationComponent
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.network.NetworkRepository
import com.gmail.yaroslavlancelot.technarium.tests.BaseTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class NetworkModuleTest : BaseTest() {
    @Inject @JvmField var networkRepository: NetworkRepository? = null
    private var mockWebServer = MockWebServer()
    private val dispatcher = NetworkDispatcher()

    @Before
    fun setUp() {
        mockWebServer.dispatcher = dispatcher
        mockWebServer.start()
        System.setProperty("javax.net.ssl.trustStoreType", "JKS")
        (DaggerTestApplicationComponent.builder().build() as DaggerTestApplicationComponent).inject(this)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testCodeguidaLoading() = runBlocking {
        val items = networkRepository!!.loadArticles(setOf(ProviderType.CODEGUIDA))
        assert(!items.any {
            it.provider() != ProviderType.CODEGUIDA
                    || it.title().isBlank()
                    || it.date().isBlank()
                    || it.description().isBlank()
                    || it.link().isBlank()
        }) { "codeguida parser returned malformed items" }
        assert(items.size == 40) { "codeguida parser returned wrong amount of items" }
    }
}

private class NetworkDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                getInstrumentation().context.assets.open("rss/response_articles_codeguida.rss")
                    .source().buffer().buffer
            )
    }
}