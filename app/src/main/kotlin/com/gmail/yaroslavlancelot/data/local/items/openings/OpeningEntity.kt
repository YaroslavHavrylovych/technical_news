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

package com.gmail.yaroslavlancelot.data.local.items.openings

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.yaroslavlancelot.data.ItemType
import com.gmail.yaroslavlancelot.data.ProviderType
import com.gmail.yaroslavlancelot.data.local.items.posts.PostEntity
import com.gmail.yaroslavlancelot.screens.itemslist.openings.filter.Category
import com.gmail.yaroslavlancelot.screens.itemslist.openings.filter.Experience
import com.gmail.yaroslavlancelot.screens.itemslist.openings.filter.Location
import java.util.*

@Entity(tableName = "opening", inheritSuperIndices = true)
class OpeningEntity(
    link: String,
    type: ItemType,
    provider: ProviderType,
    title: String,
    description: String,
    pubDate: Date,
    selected: Boolean,
    @ColumnInfo(name = "query") val query: String?,
    @ColumnInfo(name = "category") val category: Category?,
    @ColumnInfo(name = "location") val location: Location?,
    @ColumnInfo(name = "experience") val experience: Experience?
) : PostEntity(link, type, provider, title, description, pubDate, selected)