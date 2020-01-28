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

package com.gmail.yaroslavlancelot.technarium.data.local.items.openings

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.gmail.yaroslavlancelot.technarium.data.ItemType
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.PostEntity
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Category
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Experience
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Location
import java.util.*

@Entity(tableName = "opening", inheritSuperIndices = true)
class OpeningEntity : PostEntity {
    @ColumnInfo(name = "query") val query: String
    @ColumnInfo(name = "category") val category: Category
    @ColumnInfo(name = "location") val location: Location
    @ColumnInfo(name = "experience") val experience: Experience

    constructor(
        link: String,
        provider: ProviderType,
        title: String,
        description: String,
        pubDate: Date,
        selected: Boolean,
        query: String,
        category: Category,
        location: Location,
        experience: Experience
    ) : super(link, ItemType.OPENING, provider, title, description, pubDate, selected) {
        this.query = query
        this.category = category
        this.location = location
        this.experience = experience
    }

    constructor(newEntity: OpeningEntity, oldEntity: OpeningEntity)
            : super(
        newEntity.link,
        ItemType.OPENING,
        newEntity.provider,
        newEntity.title,
        newEntity.description,
        newEntity.pubDate,
        oldEntity.selected
    ) {
        this.query = if (newEntity.query.isEmpty()) newEntity.query else oldEntity.query
        this.category = if (newEntity.category == Category.NONE) newEntity.category else oldEntity.category
        this.location = if (newEntity.location == Location.NONE) newEntity.location else oldEntity.location
        this.experience = if (newEntity.query.isEmpty()) newEntity.experience else oldEntity.experience
    }
}