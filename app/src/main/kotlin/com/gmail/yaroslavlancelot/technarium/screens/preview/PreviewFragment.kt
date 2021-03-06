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

package com.gmail.yaroslavlancelot.technarium.screens.preview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseFragment
import com.gmail.yaroslavlancelot.technarium.utils.extensions.htmlDecode
import kotlinx.android.synthetic.main.lt_item_preview_fragment.*

class PreviewFragment : BaseFragment() {
    private val args: PreviewFragmentArgs by navArgs()

    override fun getLayoutId() = R.layout.lt_item_preview_fragment

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        web_view.webViewClient = WebViewClient()
        web_view.settings.javaScriptEnabled = true
        web_view.loadUrl(args.articleUrl)
        (activity as AppCompatActivity).supportActionBar?.title = args.title.htmlDecode()
    }
}
