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

package com.gmail.yaroslavlancelot.technarium.data.local.items

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

open class BaseEntity(
    @ColumnInfo(name = "link") val link: String
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    override fun hashCode(): Int {
        return link.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is BaseEntity) return false
        return link == other.link
    }
}
