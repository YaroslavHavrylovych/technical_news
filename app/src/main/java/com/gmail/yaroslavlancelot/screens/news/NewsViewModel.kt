package com.gmail.yaroslavlancelot.screens.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.yaroslavlancelot.network.articles.ArticlesRepository
import com.gmail.yaroslavlancelot.network.articles.IArticle
import com.gmail.yaroslavlancelot.screens.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel
@Inject constructor(private val repository: ArticlesRepository) : BaseViewModel() {
    private val articles: MutableLiveData<List<IArticle>> = MutableLiveData()

    fun getArticles(): LiveData<List<IArticle>> {
        launch(coroutineContext) {
            articles.value = repository.loadArticles()
        }
        return articles
    }
}