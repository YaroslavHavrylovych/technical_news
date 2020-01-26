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

package com.gmail.yaroslavlancelot.technarium.screens.itemslist.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.gmail.yaroslavlancelot.technarium.data.DataRepository
import com.gmail.yaroslavlancelot.technarium.utils.extensions.observe
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.PostEntity
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.BaseItemsListFragment
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.ItemsListAdapter
import kotlinx.android.synthetic.main.lt_items_fragment.*


class NewsListFragment : BaseItemsListFragment<PostEntity>() {
    private val viewModel: NewsViewModel by viewModels(factoryProducer = { viewModelFactory })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.getNews()) { news ->
            news_recycler_view.adapter = ItemsListAdapter(news, ::onItemClicked)
        }
        observe(viewModel.loadingStatus()) {
            loading_indicator_view.visibility = if (it == DataRepository.LoadingStatus.LOADING) View.VISIBLE else View.GONE
        }
        viewModel.refresh()
    }

    override fun onItemClicked(item: PostEntity) {
        view?.findNavController()?.navigate(
            NewsListFragmentDirections.actionNewsToPreview(item.link)
        )
    }
}