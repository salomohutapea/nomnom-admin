package com.example.nomnom.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.nomnom.R

class PresensiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_presensi, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myWebView: WebView? = view.findViewById(R.id.webview_presensi)
        myWebView?.webViewClient = WebViewClient()
        myWebView?.loadUrl("https://nomnomattendance.herokuapp.com/")
        myWebView?.settings?.javaScriptEnabled = true
    }

}