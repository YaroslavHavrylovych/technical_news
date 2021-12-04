package com.gmail.yaroslavlancelot.technarium.news

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NewsViewModelTest {

    @Test
    fun `test displayed text in News`() {
        val vm = NewsViewModel()
        assertThat(vm.displayedString == "Hello News")
    }
}