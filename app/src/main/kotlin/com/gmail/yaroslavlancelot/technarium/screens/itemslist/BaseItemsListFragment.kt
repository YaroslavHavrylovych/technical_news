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

package com.gmail.yaroslavlancelot.technarium.screens.itemslist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.data.DataRepository
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.PostEntity
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseFragment
import com.gmail.yaroslavlancelot.technarium.screens.base.ItemsViewModel
import com.gmail.yaroslavlancelot.technarium.utils.extensions.observe
import kotlinx.android.synthetic.main.lt_items_fragment.loading_indicator_view
import kotlinx.android.synthetic.main.lt_items_fragment.news_recycler_view

abstract class BaseItemsListFragment<T : PostEntity> : BaseFragment() {
    override fun getLayoutId() = R.layout.lt_items_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        getViewModel().getItems().removeObservers(this)
        observe(getViewModel().getItems()) {
            getAdapter()?.updateItems(it)
        }
        observe(getViewModel().loadingStatus()) {
            val loading = it == DataRepository.LoadingStatus.LOADING
            loading_indicator_view?.visibility = if (loading) View.VISIBLE else View.GONE
        }
        getViewModel().refresh()
        initNewsRecyclerView()
    }

    protected abstract fun onItemClicked(item: T)

    protected abstract fun setAdapter()

    private fun getAdapter(): ItemsListAdapter<T>? = news_recycler_view.adapter as ItemsListAdapter<T>?

    protected abstract fun getViewModel(): ItemsViewModel<T>

    private fun initNewsRecyclerView() {
        news_recycler_view.layoutManager = LinearLayoutManager(context)
        news_recycler_view.itemAnimator = DefaultItemAnimator()
    }
}

