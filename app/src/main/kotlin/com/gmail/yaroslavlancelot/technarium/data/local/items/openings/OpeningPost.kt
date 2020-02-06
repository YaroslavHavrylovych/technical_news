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
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.Post
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Category
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Experience
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Location
import java.util.*

@Entity(tableName = "opening", inheritSuperIndices = true)
class OpeningPost(
    link: String,
    provider: ProviderType,
    title: String,
    description: String,
    pubDate: Date,
    selected: Boolean,
    @ColumnInfo(name = "query") var query: String?,
    @ColumnInfo(name = "category") var category: Category?,
    @ColumnInfo(name = "location") var location: Location?,
    @ColumnInfo(name = "experience") var experience: Experience?
) : Post(link, ItemType.OPENING, provider, title, description, pubDate, selected) {

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is OpeningPost) return false
        return super.equals(other)
                && query == other.query
                && category == other.category
                && location == other.location
                && experience == other.experience
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (query?.hashCode() ?: 0)
        result = 31 * result + (category?.hashCode() ?: 0)
        result = 31 * result + (location?.hashCode() ?: 0)
        result = 31 * result + (experience?.hashCode() ?: 0)
        return result
    }

    companion object {
        fun fromEntities(newEntity: OpeningPost, oldEntity: OpeningPost) = OpeningPost(
            newEntity.link, newEntity.provider, newEntity.title,
            newEntity.description, newEntity.pubDate, oldEntity.selected || newEntity.selected,
            if (newEntity.query?.isEmpty() != false) oldEntity.query else newEntity.query,
            if (newEntity.category == Category.NONE) oldEntity.category else newEntity.category,
            if (newEntity.location == Location.NONE) oldEntity.location else newEntity.location,
            if (newEntity.query?.isEmpty() != false) oldEntity.experience else newEntity.experience
        )
    }
}