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

package com.gmail.yaroslavlancelot.technarium.utils.extensions

import androidx.annotation.DrawableRes
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.data.ProviderType

@DrawableRes
fun ProviderType.getImage() = when (this) {
    ProviderType.CODEGUIDA -> R.drawable.ic_codeguida
    ProviderType.DOU -> R.drawable.ic_dou
    ProviderType.TOKAR -> R.drawable.ic_tokar
    ProviderType.PINGVIN -> R.drawable.pingvin
}
