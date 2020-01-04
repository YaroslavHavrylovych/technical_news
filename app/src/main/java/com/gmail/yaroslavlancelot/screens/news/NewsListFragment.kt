package com.gmail.yaroslavlancelot.screens.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gmail.yaroslavlancelot.R
import com.gmail.yaroslavlancelot.network.articles.ArticlesRepository
import com.gmail.yaroslavlancelot.screens.BaseFragment
import javax.inject.Inject

class NewsListFragment : BaseFragment() {
    lateinit var viewModel: NewsViewModel
    @Inject
    lateinit var articlesRepository: ArticlesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this)[NewsViewModel::class.java]
        viewModel.repository = articlesRepository
        viewModel.getArticles().observe(this, Observer { articles ->
            println("articles: ${articles.size}")
        })
        return inflater.inflate(R.layout.layout_news, container, false)
    }
}