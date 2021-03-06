package com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Filter
import androidx.annotation.LayoutRes
import androidx.cardview.widget.CardView
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.OpeningsViewModel
import com.gmail.yaroslavlancelot.technarium.utils.extensions.addListener
import com.gmail.yaroslavlancelot.technarium.utils.extensions.getReferenceColor
import kotlinx.android.synthetic.main.lt_filter.view.apply_button
import kotlinx.android.synthetic.main.lt_filter.view.category_spinner
import kotlinx.android.synthetic.main.lt_filter.view.clear_filters_button
import kotlinx.android.synthetic.main.lt_filter.view.experience_spinner
import kotlinx.android.synthetic.main.lt_filter.view.filter_container
import kotlinx.android.synthetic.main.lt_filter.view.location_spinner
import kotlinx.android.synthetic.main.lt_filter.view.search_text_view
import kotlin.math.hypot


class FilterCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val revealDuration = 500L
    private var listener: ((s: FilterViewStates) -> Unit)? = null
    private var viewModel: OpeningsViewModel? = null
    private val categories: Array<String> by lazy { resources.getStringArray(R.array.opening_category) }
    private val locations: Array<String> by lazy { resources.getStringArray(R.array.opening_location) }
    private val experience: Array<String> by lazy { resources.getStringArray(R.array.opening_experience) }

    init {
        View.inflate(context, R.layout.lt_filter, this)
        clear_filters_button.setOnClickListener { clearFilter() }
        apply_button.setOnClickListener { applyClicked() }
    }

    fun clearView() {
        listener = null
        viewModel = null
    }

    fun revealView(viewModel: OpeningsViewModel, callback: (s: FilterViewStates) -> Unit, withAnimation: Boolean = true) {
        listener = callback
        this.viewModel = viewModel
        fullFill(viewModel)
        if (withAnimation) {
            addOnLayoutChangeListener(object : OnLayoutChangeListener {
                override fun onLayoutChange(
                    v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int,
                    oldRight: Int, oldBottom: Int
                ) {
                    v.removeOnLayoutChangeListener(this)
                    listener?.invoke(FilterViewStates.REVEAL_ANIMATION)
                    //reveal
                    val fabRadius = context.resources.getDimension(R.dimen.filter_fab) / 2
                    val cx = v.width - fabRadius - v.filter_container.paddingEnd
                    val cy = v.height - fabRadius - v.filter_container.paddingBottom
                    val radius = hypot(right.toDouble(), bottom.toDouble()).toInt()
                    ViewAnimationUtils.createCircularReveal(
                        v, cx.toInt(), cy.toInt(), fabRadius, radius.toFloat()
                    ).apply {
                        duration = revealDuration
                        start()
                    }
                    //color
                    ValueAnimator().apply {
                        setIntValues(getFabColor(), context.getReferenceColor(R.attr.colorOnBackground))
                        setEvaluator(ArgbEvaluator())
                        duration = revealDuration
                        addUpdateListener { (v as CardView).setCardBackgroundColor(it.animatedValue as Int) }
                        start()
                    }
                }
            })
        } else {
            callback.invoke(FilterViewStates.REVEAL_ANIMATION)
        }
        visibility = View.VISIBLE
    }

    fun concealView() {
        //reveal
        val fabRadius = context.resources.getDimension(R.dimen.filter_fab) / 2
        val cx = width - fabRadius - filter_container.paddingEnd
        val cy = height - fabRadius - filter_container.paddingBottom
        val radius = hypot(right.toDouble(), bottom.toDouble()).toInt()
        ViewAnimationUtils.createCircularReveal(
            this, cx.toInt(), cy.toInt(), radius.toFloat(), fabRadius
        ).apply {
            duration = revealDuration
            start()
        }
        //color
        ValueAnimator().apply {
            setIntValues(context.getReferenceColor(R.attr.colorOnBackground), getFabColor())
            setEvaluator(ArgbEvaluator())
            duration = revealDuration
            addUpdateListener { setCardBackgroundColor(it.animatedValue as Int) }
            addListener(onEnd = {
                listener?.invoke(FilterViewStates.HIDDEN)
                clearView()
                visibility = View.GONE
            })
            start()
        }
    }

    private fun getFabColor() = context.getReferenceColor(if (viewModel?.isFiltered() == true) R.attr.colorFiltered else R.attr.colorAccent)

    private fun fullFill(vm: OpeningsViewModel) {
        val updateTv = { pos: Int, array: Array<String>, tv: AutoCompleteTextView -> tv.setText(array[pos - 1], false) }
        //category
        category_spinner.setOnClickListener { resetFocus() }
        category_spinner.setAdapter(NotFilteringArrayAdapter(context!!, R.layout.lt_spinner_item, categories))
        if (vm.getCategory() != Category.NONE) updateTv(vm.getCategory().ordinal, categories, category_spinner)
        //location
        location_spinner.setOnClickListener { resetFocus() }
        location_spinner.setAdapter(NotFilteringArrayAdapter(context!!, R.layout.lt_spinner_item, locations))
        if (vm.getLocation() != Location.NONE) updateTv(vm.getLocation().ordinal, locations, location_spinner)
        //experience
        experience_spinner.setOnClickListener { resetFocus() }
        experience_spinner.setAdapter(NotFilteringArrayAdapter(context!!, R.layout.lt_spinner_item, experience))
        if (vm.getExperience() != Experience.NONE) updateTv(vm.getExperience().ordinal, experience, experience_spinner)
    }

    private fun applyClicked() {
        val getInd = { name: String, array: Array<String> -> if (name == "") 0 else (array.indexOf(name) + 1) }
        viewModel?.applyFilter(
            search_text_view.text.toString(),
            Category.values()[getInd(category_spinner.text.toString(), categories)],
            Location.values()[getInd(location_spinner.text.toString(), locations)],
            Experience.values()[getInd(experience_spinner.text.toString(), experience)]
        )
        concealView()
    }

    override fun onViewRemoved(child: View?) {
        clearView()
        super.onViewRemoved(child)
    }

    private fun resetFocus() {
        search_text_view.clearFocus()
        val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(search_text_view.windowToken, 0)
    }

    private fun clearFilter() {
        search_text_view.text?.clear()
        category_spinner.setText("", false)
        location_spinner.setText("", false)
        experience_spinner.setText("", false)
    }

    enum class FilterViewStates {
        REVEAL_ANIMATION, HIDDEN
    }

    /** ArrayAdapter which prevents any content filtering */
    class NotFilteringArrayAdapter<T>(context: Context, @LayoutRes layout: Int, private val items: Array<T>) : android.widget.ArrayAdapter<T>(context, layout, items) {
        private val filter = StubFilter()

        override fun getFilter(): Filter = filter

        private inner class StubFilter : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val res = FilterResults()
                res.count = items.size
                res.values = items
                return res
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {}
        }
    }

}