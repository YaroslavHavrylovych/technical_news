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

package com.gmail.yaroslavlancelot.screens.itemslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.UiThread
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.yaroslavlancelot.R
import com.gmail.yaroslavlancelot.data.network.items.IItem
import com.gmail.yaroslavlancelot.screens.base.BaseFragment
import kotlinx.android.synthetic.main.lt_items_fragment.news_recycler_view
import kotlinx.android.synthetic.main.progress_bar_view.progress_bar


abstract class BaseItemsListFragment : BaseFragment(), DataLoader {
    override fun getLayoutId() = R.layout.lt_items_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNewsRecyclerView()
    }

    private fun initNewsRecyclerView() {
        news_recycler_view.layoutManager = LinearLayoutManager(context)
        news_recycler_view.itemAnimator = DefaultItemAnimator()
        news_recycler_view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    @UiThread
    override fun loadingStarted() {
        progress_bar?.visibility = View.VISIBLE
    }

    @UiThread
    override fun loadingDone() {
        progress_bar?.visibility = View.GONE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        observableModel().addObserver(this)
    }

    override fun onDetach() {
        observableModel().removeObserver(this)
        super.onDetach()
    }

    protected abstract fun onItemClicked(item: IItem)

    protected abstract fun observableModel(): ObservableData
}

@UiThread
interface DataLoader {
    fun loadingStarted()

    fun loadingDone()
}

@UiThread
interface ObservableData {
    fun addObserver(observer: DataLoader)

    fun removeObserver(observer: DataLoader)
}

