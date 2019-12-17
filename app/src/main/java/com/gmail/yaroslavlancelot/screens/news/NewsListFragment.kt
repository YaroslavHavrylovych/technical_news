package com.gmail.yaroslavlancelot.screens.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gmail.yaroslavlancelot.R
import com.gmail.yaroslavlancelot.screens.BaseFragment

class NewsListFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val model = ViewModelProviders.of(this)[NewsViewModel::class.java]
        model.getArticles().observe(this, Observer { articles ->
            println("articles: ${articles.size}")
        })

        return inflater.inflate(R.layout.layout_news, container, false)
    }
}