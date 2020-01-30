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

package com.gmail.yaroslavlancelot.technarium.data.local.items.posts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.gmail.yaroslavlancelot.technarium.data.ItemType
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import java.util.*

@Entity(tableName = "post", indices = [(Index(value = ["link"], unique = true))])
open class PostEntity(
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "type") var type: ItemType,
    @ColumnInfo(name = "provider") val provider: ProviderType,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "pub_date") var pubDate: Date,
    @ColumnInfo(name = "selected") var selected: Boolean
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    override fun hashCode(): Int {
        return link.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is PostEntity) return false
        return link == other.link
                && type == other.type
                && provider == other.provider
                && title == other.title
                && description == other.description
                && pubDate.time == other.pubDate.time
                && selected == other.selected
    }
}