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

import android.content.Context
import androidx.room.Room
import com.gmail.yaroslavlancelot.data.local.items.ItemDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {
    @Provides
    internal fun provideLocalRepository(dao: ItemDao): LocalRepository = LocalRepositoryImpl(dao)

    @Provides
    @Singleton
    internal fun provideDao(db: AppDatabase): ItemDao = db.itemDao()

    @Provides
    @Singleton
    internal fun provideDb(applicationContext: Context): AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "technarium_database"
    ).build()
}