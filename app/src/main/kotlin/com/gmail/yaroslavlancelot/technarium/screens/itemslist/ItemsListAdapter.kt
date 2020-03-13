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

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.setMargins
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.Post
import com.gmail.yaroslavlancelot.technarium.utils.extensions.getImage
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.ItemsListAdapter.ItemViewHolder
import kotlinx.android.synthetic.main.lt_items_list_item.view.*
import timber.log.Timber
import xyz.hanks.library.bang.SmallBangView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

typealias ImageSelected = (selected: Boolean) -> Int

class ItemsListAdapter<T : Post>
private constructor(
    private val items: MutableList<T>,
    //TODO this not used only for a short period of time (until I figure out where to trigger)
    private val onItemClickListener: ((item: T) -> Unit)? = null,
    private val onSelectClickListener: ((item: T) -> Unit)? = null,
    private val imageSelected: ImageSelected
) : Adapter<ItemViewHolder>() {
    private lateinit var dateFormat: SimpleDateFormat
    private var extendedItems = ArrayList<String>(10)
    private var extendedMargin = 0f
    private var collapsedMargin = 0f

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        collapsedMargin = parent.resources.getDimension(R.dimen.list_item_margin)
        extendedMargin = parent.resources.getDimension(R.dimen.list_item_extended_margin)
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
        holder.continueButton.setOnClickListener { onItemClickListener?.invoke(item) }
        holder.selectedImage.setImageResource(imageSelected(item.selected))
        holder.selectionArea.setOnClickListener { selectedClicked(item.link, holder) }
        val date = item.pubDate.forList()
        //TODO codeguida doesn't have pubDate
        if (item.provider == ProviderType.CODEGUIDA || date == null) holder.date.setText(R.string.item_date_unknown)
        else holder.date.text = date
        //extension: content
        holder.itemView.setOnClickListener {
            if (!extendedItems.remove(item.link)) extendedItems.add(item.link)
            updateLayout(item, holder)
            notifyItemChanged(position)
        }
        updateLayout(item, holder)
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
        diffResult.dispatchUpdatesTo(this)
        items.clear()
        items.addAll(newItems)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title_view
        val image: ImageView = itemView.image_view
        val date: TextView = itemView.pub_date_view
        val selectedImage: ImageView = itemView.selected_view
        val bangView: SmallBangView = itemView.selected_view_bang
        val selectionArea: View = itemView.selection_area_view
        val content: TextView = itemView.content
        val continueButton: TextView = itemView.continue_reading
        val separator: View = itemView.separator
    }

    private fun updateLayout(item: T, holder: ItemViewHolder) {
        val lp = holder.itemView.layoutParams as RecyclerView.LayoutParams
        val extend = extendedItems.contains(item.link)
        val margin: Int
        val visibility: Int
        if (extend) {
            visibility = View.VISIBLE
            holder.content.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(item.description, Html.FROM_HTML_MODE_COMPACT)
                else Html.fromHtml(item.description)
            margin = extendedMargin.toInt()
        } else {
            visibility = View.GONE
            holder.content.text = null
            margin = collapsedMargin.toInt()
        }
        holder.continueButton.visibility = visibility
        holder.content.visibility = visibility
        holder.continueButton.visibility = visibility
        holder.separator.visibility = visibility
        lp.setMargins(margin)
    }

    private fun selectedClicked(link: String, holder: ItemViewHolder) {
        val item = items.find { it.link == link } ?: return
        item.selected = !item.selected
        if (item.selected) holder.bangView.likeAnimation()
        holder.selectedImage.setImageResource(imageSelected(item.selected))
        onSelectClickListener?.invoke(item)
    }

    private fun Date.forList(): String? {
        try {
            return dateFormat.format(this)
        } catch (ex: ParseException) {
            Timber.e(ex)
        }
        return null
    }

    class Builder<T : Post>(
        private var items: MutableList<T> = ArrayList(),
        private var onItemClickListener: ((item: T) -> Unit)? = null,
        private var onSelectClickListener: ((item: T) -> Unit)? = null,
        private var imageSelected: ImageSelected = ::defaultImageSelector
    ) {
        fun imageSelector(imageSelected: ImageSelected) = apply { this.imageSelected = imageSelected }

        fun itemClick(listener: (item: T) -> Unit) = apply { this.onItemClickListener = listener }

        fun selectClick(listener: (item: T) -> Unit) = apply { this.onSelectClickListener = listener }

        fun build() = ItemsListAdapter(items, onItemClickListener, onSelectClickListener, imageSelected)
    }

    companion object {
        @DrawableRes
        private fun defaultImageSelector(selected: Boolean): Int = if (selected) R.drawable.ic_heart_selected else R.drawable.ic_heart_unselected
    }
}


private class ItemDiffUtilCallback(
    private val oldList: List<Post>,
    private val newList: List<Post>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].link == newList[newItemPosition].link

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
}