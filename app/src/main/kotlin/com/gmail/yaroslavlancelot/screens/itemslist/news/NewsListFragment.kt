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

package com.gmail.yaroslavlancelot.screens.itemslist.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.gmail.yaroslavlancelot.R
import com.gmail.yaroslavlancelot.extensions.observe
import com.gmail.yaroslavlancelot.data.network.items.IItem
import com.gmail.yaroslavlancelot.screens.itemslist.BaseItemsListFragment
import com.gmail.yaroslavlancelot.screens.itemslist.ItemsListAdapter
import kotlinx.android.synthetic.main.lt_items_fragment.*


class NewsListFragment : BaseItemsListFragment() {
    private val viewModel: NewsViewModel by viewModels(factoryProducer = { viewModelFactory })

    override fun getLayoutId() = R.layout.lt_items_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.getNews()) { news ->
            news_recycler_view.adapter = ItemsListAdapter(news, ::onItemClicked)
        }
    }

    override fun onItemClicked(item: IItem) {
        view?.findNavController()?.navigate(
            NewsListFragmentDirections.actionNewsToPreview(item.getLink())
        )
    }

}