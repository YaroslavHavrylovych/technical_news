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

package com.gmail.yaroslavlancelot.technarium.data.local.items.events

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.gmail.yaroslavlancelot.technarium.data.ItemType
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.PostEntity
import java.util.*

@Entity(tableName = "event", inheritSuperIndices = true)
class EventEntity(
    link: String,
    type: ItemType,
    provider: ProviderType,
    title: String,
    description: String,
    pubDate: Date,
    selected: Boolean,
    @ColumnInfo(name = "start_date") val startDate: Date?,
    @ColumnInfo(name = "end_date") val endDate: Date?
) : PostEntity(link, type, provider, title, description, pubDate, selected)