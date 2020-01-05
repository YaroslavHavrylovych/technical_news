package com.gmail.yaroslavlancelot.screens.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.gmail.yaroslavlancelot.R
import com.gmail.yaroslavlancelot.screens.BaseFragment
import javax.inject.Inject

class NewsListFragment : BaseFragment() {
    @Inject
    lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.layout_news, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModel(viewModelFactory) {
            observe(viewModel.getArticles()) { articles ->
                println("articles: ${articles?.size}")
            }
        }
    }

    fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))

    inline fun <reified T : ViewModel> Fragment.viewModel(
        factory: ViewModelProvider.Factory,
        body: T.() -> Unit
    ): T {
        val vm = ViewModelProviders.of(this, factory)[T::class.java]
        vm.body()
        return vm
    }
}