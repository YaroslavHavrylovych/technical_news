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

package com.gmail.yaroslavlancelot.screens.articles.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.yaroslavlancelot.R
import com.gmail.yaroslavlancelot.extensions.observe
import com.gmail.yaroslavlancelot.data.network.articles.IArticle
import com.gmail.yaroslavlancelot.screens.BaseFragment
import kotlinx.android.synthetic.main.lt_news_fragment.*


class ArticlesListFragment : BaseFragment() {
    private val viewModel: ArticlesViewModel by viewModels(factoryProducer = { viewModelFactory })


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.lt_news_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNewsRecyclerView()
        observe(viewModel.getArticles()) { articles ->
            news_recycler_view.adapter = ArticlesListAdapter(articles, ::onArticleClicked)
        }
    }

    private fun initNewsRecyclerView() {
        news_recycler_view.layoutManager = LinearLayoutManager(context)
        news_recycler_view.itemAnimator = DefaultItemAnimator()
        news_recycler_view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    private fun onArticleClicked(article: IArticle) {
        view?.findNavController()?.navigate(
            ArticlesListFragmentDirections.actionArticlesToPreview(article.getLink())
        )
    }
}