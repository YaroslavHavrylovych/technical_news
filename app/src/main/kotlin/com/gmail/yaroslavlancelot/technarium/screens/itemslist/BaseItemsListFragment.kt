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
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.data.DataRepository
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.Post
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseFragment
import com.gmail.yaroslavlancelot.technarium.screens.base.ItemsViewModel
import com.gmail.yaroslavlancelot.technarium.utils.extensions.observe
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.lt_items_fragment.info_empty
import kotlinx.android.synthetic.main.lt_items_fragment.loading_indicator_view
import kotlinx.android.synthetic.main.lt_items_fragment.news_recycler_view

abstract class BaseItemsListFragment<T : Post> : BaseFragment() {
    override fun getLayoutId() = R.layout.lt_items_fragment
    private var adapter: ItemsListAdapter<T>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = constructBuilder().build()
        news_recycler_view.adapter = ScaleInAnimationAdapter(adapter)
        getViewModel().getItems().removeObservers(this)
        observe(getViewModel().getItems()) { updateFragmentWithItems(it) }
        observe(getViewModel().loadingStatus()) { updateFragmentWithLoadingStatus(it) }
        getViewModel().refresh()
        initNewsRecyclerView()
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }

    protected abstract fun onItemClicked(item: T)

    protected fun onSelectClicked(item: T) = getViewModel().updateItem(item)

    protected abstract fun constructBuilder(): ItemsListAdapter.Builder<T>

    private fun getAdapter(): ItemsListAdapter<T>? = adapter

    protected abstract fun getViewModel(): ItemsViewModel<T>

    private fun updateFragmentWithItems(items: List<T>?) {
        if (getAdapter()?.itemCount == 0) getAdapter()?.setItems(items)
        else getAdapter()?.updateItems(items)
        if (items?.isNotEmpty() != false) info_empty.visibility = View.INVISIBLE
    }

    private fun updateFragmentWithLoadingStatus(loadingStatus: DataRepository.LoadingStatus?) {
        if (loadingStatus == DataRepository.LoadingStatus.ERROR)
            Toast.makeText(requireContext(), R.string.loading_error, Toast.LENGTH_LONG).show()
        val loading = loadingStatus == DataRepository.LoadingStatus.LOADING
        loading_indicator_view?.visibility = if (loading) View.VISIBLE else View.GONE
        if (loadingStatus != DataRepository.LoadingStatus.LOADING
            && loadingStatus != DataRepository.LoadingStatus.NONE
            && getAdapter()?.itemCount == 0
        ) info_empty.visibility = View.VISIBLE
    }

    private fun initNewsRecyclerView() {
        news_recycler_view.layoutManager = LinearLayoutManager(context)
        news_recycler_view.itemAnimator = LandingAnimator()
    }
}

