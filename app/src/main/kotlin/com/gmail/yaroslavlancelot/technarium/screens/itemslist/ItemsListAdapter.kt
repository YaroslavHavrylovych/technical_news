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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.PostEntity
import com.gmail.yaroslavlancelot.technarium.utils.extensions.getImage
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.ItemsListAdapter.ItemViewHolder
import kotlinx.android.synthetic.main.lt_items_list_item.view.*
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ItemsListAdapter<T : PostEntity>(
    private val items: MutableList<T>,
    private val onItemClickListener: (item: T) -> Unit
) : Adapter<ItemViewHolder>() {
    private lateinit var dateFormat: SimpleDateFormat

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        dateFormat = SimpleDateFormat(
            parent.resources.getString(R.string.item_date_format),
            Locale(parent.resources.getString(R.string.item_date_language), parent.resources.getString(R.string.item_date_country))
        )
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.lt_items_list_item, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.image.setImageResource(items[position].getImage())
        holder.itemView.setOnClickListener { onItemClickListener(items[position]) }
        val date = items[position].pubDate.forList()
        if (date == null) holder.date.setText(R.string.item_date_unknown)
        else holder.date.text = date
    }

    fun updateItems(newItems: List<T>?) {
        if (newItems == null) {
            if (items.isEmpty()) return
            items.clear()
            notifyDataSetChanged()
            return
        }
        if (items.isEmpty() && newItems.isEmpty()) return
        val diffResult = DiffUtil.calculateDiff(ItemDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title_view
        val image: ImageView = itemView.image_view
        val date: TextView = itemView.pub_date_view
    }

    private fun Date.forList(): String? {
        try {
            return dateFormat.format(this)
        } catch (ex: ParseException) {
            Timber.e(ex)
        }
        return null
    }
}

private class ItemDiffUtilCallback(
    private val oldList: List<PostEntity>,
    private val newList: List<PostEntity>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].link == newList[newItemPosition].link

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
}