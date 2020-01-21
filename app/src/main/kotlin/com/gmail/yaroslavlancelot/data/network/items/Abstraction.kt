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
import com.gmail.yaroslavlancelot.data.network.items.providers.DouItem
import com.gmail.yaroslavlancelot.data.network.items.providers.TokarItem
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


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
        return item.link.replace("http://", "https://")
    }

    override fun getProviderImage(): Int {
        return R.drawable.ic_codeguida
    }

    override fun getPublicationDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -20)
        return calendar.time
    }
}

internal class TokarItemImpl(private val item: TokarItem) : IItem {
    private val dateFormat = SimpleDateFormat(
        //Mon, 16 Dec 2019 07:00:43 +0000
        //Thu, 23 Jan 2020 11:00:05 +0000
        "EEE, dd MMM yyyy HH:mm:ss Z",
        Locale.ENGLISH
    )

    override fun getTitle(): String {
        return item.title
    }

    override fun getLink(): String {
        return item.link
    }

    override fun getProviderImage(): Int {
        return R.drawable.ic_tokar
    }

    override fun getPublicationDate(): Date {
        return Helper.parseDate(item.date)
    }
}

internal class DouItemImpl(private val item: DouItem) : IItem {
    private val dateFormat = SimpleDateFormat(
        //Mon, 16 Dec 2019 07:00:43 +0000
        "EEE, dd MMM yyyy HH:mm:ss Z",
        Locale.ENGLISH
    )

    override fun getTitle(): String {
        return item.title
    }

    override fun getLink(): String {
        return "${item.link}?switch_lang=uk"
    }

    override fun getProviderImage(): Int {
        return R.drawable.ic_dou
    }

    override fun getPublicationDate(): Date {
        return Helper.parseDate(item.date)
    }
}

private class Helper {
    companion object {
        //Mon, 16 Dec 2019 07:00:43 +0000
        private val dateFormat = SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss Z",
            Locale.ENGLISH
        )

        fun parseDate(strDate: String): Date {
            var date = Date()
            try {
                date = dateFormat.parse(strDate)
            } catch (ex: ParseException) {
                ex.printStackTrace()
                //TODO replace with Timber
            }
            return date
        }
    }
}



