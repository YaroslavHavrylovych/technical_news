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

package com.gmail.yaroslavlancelot.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.gmail.yaroslavlancelot.data.ItemType
import com.gmail.yaroslavlancelot.data.ProviderType
import com.gmail.yaroslavlancelot.data.local.items.ItemDao
import com.gmail.yaroslavlancelot.data.local.items.events.EventEntity
import com.gmail.yaroslavlancelot.data.local.items.openings.OpeningEntity
import com.gmail.yaroslavlancelot.data.local.items.posts.PostEntity
import com.gmail.yaroslavlancelot.screens.itemslist.openings.filter.Category
import com.gmail.yaroslavlancelot.screens.itemslist.openings.filter.Experience
import com.gmail.yaroslavlancelot.screens.itemslist.openings.filter.Location
import java.util.*


@Database(
    entities = [PostEntity::class, OpeningEntity::class, EventEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DbTypesConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}

class DbTypesConverters {
    @TypeConverter
    fun providerToString(provider: ProviderType) = provider.providerName

    @TypeConverter
    fun stringToProvider(str: String) = ProviderType.fromString(str)

    @TypeConverter
    fun itemTypeToString(itemType: ItemType) = itemType.itemName

    @TypeConverter
    fun stringToItemType(str: String) = ItemType.fromString(str)

    @TypeConverter
    fun fromTimestamp(value: Long?) = if (value == null) null else Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date?) = date?.time

    @TypeConverter
    fun categoryToString(category: Category?): String = category?.data ?: Category.NONE.data

    @TypeConverter
    fun stringToCategory(str: String?): Category = when (str) {
        Category.ANDROID.data -> Category.ANDROID
        Category.DOTNET.data -> Category.DOTNET
        Category.ANALYST.data -> Category.ANALYST
        Category.ONEC.data -> Category.ONEC
        else -> Category.NONE
    }

    @TypeConverter
    fun locationToString(location: Location?): String = location?.data ?: Location.NONE.data

    @TypeConverter
    fun stringToLocation(str: String?): Location = when (str) {
        Location.KYIV.data -> Location.KYIV
        Location.KHARKIV.data -> Location.KHARKIV
        Location.ODESA.data -> Location.ODESA
        Location.LVIV.data -> Location.LVIV
        else -> Location.NONE
    }

    @TypeConverter
    fun experienceToString(experience: Experience?): String = experience?.data ?: Experience.NONE.data

    @TypeConverter
    fun stringToExperience(str: String?): Experience = when (str) {
        Experience.JUNIOR.data -> Experience.JUNIOR
        Experience.MIDDLE.data -> Experience.MIDDLE
        Experience.SENIOR.data -> Experience.SENIOR
        Experience.EXPERT.data -> Experience.EXPERT
        else -> Experience.NONE
    }
}
