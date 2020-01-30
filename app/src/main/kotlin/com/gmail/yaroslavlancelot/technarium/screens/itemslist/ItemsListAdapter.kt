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
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.PostEntity
import com.gmail.yaroslavlancelot.technarium.utils.extensions.getImage
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.ItemsListAdapter.ItemViewHolder
import kotlinx.android.synthetic.main.lt_items_list_item.view.*
import timber.log.Timber
import xyz.hanks.library.bang.SmallBangView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ItemsListAdapter<T : PostEntity>(
    private val items: MutableList<T>,
    private val onItemClickListener: (item: T) -> Unit,
    private val onSelectClickListener: (item: T) -> Unit
) : Adapter<ItemViewHolder>() {
    private lateinit var dateFormat: SimpleDateFormat

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        dateFormat = SimpleDateFormat(
            parent.resources.getString(R.string.item_date_format),
            Locale(
                parent.resources.getString(R.string.item_date_language),
                parent.resources.getString(R.string.item_date_country)
            )
        )
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.lt_items_list_item, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.image.setImageResource(item.getImage())
        holder.itemView.setOnClickListener { onItemClickListener(item) }
        holder.selectedImage.setImageResource(getSelectionRes(item.selected))
        holder.selectionArea.setOnClickListener {
            selectedClicked(position, holder)
        }
        val date = item.pubDate.forList()
        //TODO codeguida doesn't have pubDate
        if (item.provider == ProviderType.CODEGUIDA || date == null) holder.date.setText(R.string.item_date_unknown)
        else holder.date.text = date
    }

    fun setItems(newItems: List<T>?) {
        if (newItems == null) return
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
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
        val selectedImage: ImageView = itemView.selected_view
        val bangView: SmallBangView = itemView.selected_view_bang
        val selectionArea: View = itemView.selection_area_view
    }

    private fun selectedClicked(position: Int, holder: ItemViewHolder) {
        val item = items[position]
        item.selected = !item.selected
        if (item.selected) holder.bangView.likeAnimation()
        holder.selectedImage.setImageResource(getSelectionRes(item.selected))
        onSelectClickListener(item)
    }

    private fun Date.forList(): String? {
        try {
            return dateFormat.format(this)
        } catch (ex: ParseException) {
            Timber.e(ex)
        }
        return null
    }

    @DrawableRes
    private fun getSelectionRes(selected: Boolean): Int = if (selected) R.drawable.ic_heart_selected else R.drawable.ic_heart_unselected
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