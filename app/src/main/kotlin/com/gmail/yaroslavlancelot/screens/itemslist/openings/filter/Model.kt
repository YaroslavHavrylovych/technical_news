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

package com.gmail.yaroslavlancelot.screens.itemslist.openings.filter

enum class Category(val data: String) {
    NONE(""), ANDROID("Android"), DOTNET(".NET"), ONEC("1C"), ANALYST("Analyst")
}

enum class Location(val data: String) {
    NONE(""), KYIV("Kyiv"), KHARKIV("Kharkiv"), ODESA("Odesa"), LVIV("Lviv")
}

enum class Experience(val data: String) {
    NONE(""), JUNIOR("0-1"), MIDDLE("1-3"), SENIOR("3-5"), EXPERT("5plus")
}
