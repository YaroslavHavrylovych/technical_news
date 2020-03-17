package com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Filter
import androidx.annotation.LayoutRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.OpeningsViewModel
import com.gmail.yaroslavlancelot.technarium.utils.extensions.addListener
import kotlinx.android.synthetic.main.lt_filter_fragment.view.apply_button
import kotlinx.android.synthetic.main.lt_filter_fragment.view.category_spinner
import kotlinx.android.synthetic.main.lt_filter_fragment.view.clear_filters_button
import kotlinx.android.synthetic.main.lt_filter_fragment.view.experience_spinner
import kotlinx.android.synthetic.main.lt_filter_fragment.view.filter_container
import kotlinx.android.synthetic.main.lt_filter_fragment.view.location_spinner
import kotlinx.android.synthetic.main.lt_filter_fragment.view.search_text_view
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
        View.inflate(context, R.layout.lt_filter_fragment, this)
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
                        setIntValues(getFabColor(), ContextCompat.getColor(context, R.color.onBackground))
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
            setIntValues(ContextCompat.getColor(context, R.color.onBackground), getFabColor())
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

    private fun getFabColor() =
        if (viewModel?.isFiltered() == true) ContextCompat.getColor(context, R.color.filtered)
        else ContextCompat.getColor(context, R.color.accent)

    private fun fullFill(vm: OpeningsViewModel) {
        //category
        category_spinner.setOnClickListener { resetFocus() }
        category_spinner.setAdapter(NotFilteringArrayAdapter(context!!, R.layout.lt_spinner_item, categories))
        if (vm.getCategory() != Category.NONE) category_spinner.setText(categories[vm.getCategory().ordinal], false)
        //location
        location_spinner.setOnClickListener { resetFocus() }
        location_spinner.setAdapter(NotFilteringArrayAdapter(context!!, R.layout.lt_spinner_item, locations))
        if (vm.getLocation() != Location.NONE) location_spinner.setText(locations[vm.getLocation().ordinal], false)
        //experience
        experience_spinner.setOnClickListener { resetFocus() }
        experience_spinner.setAdapter(NotFilteringArrayAdapter(context!!, R.layout.lt_spinner_item, experience))
        if (vm.getExperience() != Experience.NONE) experience_spinner.setText(experience[vm.getCategory().ordinal], false)
    }

    private fun applyClicked() {
        viewModel?.applyFilter(
            search_text_view.text.toString(),
            Category.values()[if (category_spinner.text.toString() == "") 0 else categories.indexOf(category_spinner.text.toString())],
            Location.values()[if (location_spinner.text.toString() == "") 0 else locations.indexOf(location_spinner.text.toString())],
            Experience.values()[if (experience_spinner.text.toString() == "") 0 else experience.indexOf(location_spinner.text.toString())]
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