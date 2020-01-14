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

package com.gmail.yaroslavlancelot.data.network.items

import androidx.annotation.DrawableRes
import com.gmail.yaroslavlancelot.R
import com.gmail.yaroslavlancelot.data.network.items.providers.CodeguidaItem
import com.gmail.yaroslavlancelot.data.network.items.providers.TokarItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


interface IItem {
    fun getTitle(): String

    fun getLink(): String

    @DrawableRes
    fun getProviderImage(): Int

    fun getPublicationDate(): Date
}

internal class CodeguidaItemImpl(private val item: CodeguidaItem) : IItem {
    override fun getTitle(): String {
        return item.title
    }

    override fun getLink(): String {
        return item.link
    }

    override fun getProviderImage(): Int {
        return R.drawable.codeguida_logo
    }

    override fun getPublicationDate(): Date {
        //TODO this is not implemented on codeguidate side
        return Date()
    }
}

internal class TokarItemImpl(private val item: TokarItem) : IItem {
    private val dateFormat = SimpleDateFormat(
        //Mon, 16 Dec 2019 07:00:43 +0000
        "EEE, dd MMM yyyy HH:mm:ss Z",
        Locale.getDefault()
    )

    override fun getTitle(): String {
        return item.title
    }

    override fun getLink(): String {
        return item.link
    }

    override fun getProviderImage(): Int {
        return R.drawable.tokar_logo
    }

    override fun getPublicationDate(): Date {
        return dateFormat.parse(item.date)
    }
}
