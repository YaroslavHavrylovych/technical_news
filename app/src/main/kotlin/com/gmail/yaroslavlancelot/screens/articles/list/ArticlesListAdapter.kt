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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gmail.yaroslavlancelot.R.layout
import com.gmail.yaroslavlancelot.data.network.articles.IArticle
import com.gmail.yaroslavlancelot.screens.articles.list.ArticlesListAdapter.ArticleViewHolder
import kotlinx.android.synthetic.main.lt_news_list_item.view.*

class ArticlesListAdapter(
    private val articles: List<IArticle>?,
    private val onItemClickListener: (article: IArticle) -> Unit
) : Adapter<ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(LayoutInflater.from(parent.context).inflate(layout.lt_news_list_item, parent, false))

    override fun getItemCount(): Int = articles?.size ?: 0

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        requireNotNull(articles)
        holder.title.text = articles[position].getTitle()
        holder.image.setImageResource(articles[position].getProviderImage())
        holder.itemView.setOnClickListener { onItemClickListener(articles[position]) }
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title_view
        val image: ImageView = itemView.image_view
    }
}