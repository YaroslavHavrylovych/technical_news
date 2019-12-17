package com.gmail.yaroslavlancelot.screens.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.yaroslavlancelot.network.articles.ArticlesFactory
import com.gmail.yaroslavlancelot.network.articles.IArticle
import com.gmail.yaroslavlancelot.screens.BaseViewModel
import kotlinx.coroutines.launch

class NewsViewModel : BaseViewModel() {
    private val articles: MutableLiveData<List<IArticle>> = MutableLiveData()

    fun getArticles(): LiveData<List<IArticle>> {
        launch(coroutineContext) {
            articles.value = ArticlesFactory.loadArticles()
        }
        return articles
    }
}