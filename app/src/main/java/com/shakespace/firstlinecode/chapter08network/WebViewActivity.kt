package com.shakespace.firstlinecode.chapter08network

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.TAG
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        initWebView()
        webview.loadUrl("https://www.baidu.com")

    }


    fun setCookie(uri: Uri, cookie: String) {

        val host = uri.host

        val cookieManager = CookieManager.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptCookie(true)
            cookieManager.removeAllCookies(null)
            cookieManager.removeSessionCookies(null)
            cookieManager.setCookie(host, cookie)
            cookieManager.flush()
        } else {
            val cookieSyncManager = CookieSyncManager.createInstance(this)
            cookieManager.removeSessionCookie()
            cookieManager.removeAllCookie()
            cookieManager.setCookie(host, cookie)
            cookieSyncManager.sync()
        }
    }

    @Suppress("DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView() {
        webview.settings.javaScriptEnabled = true
        webview.settings.domStorageEnabled = true


        webview.webViewClient = object : WebViewClient() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Nullable
            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                Log.d(this.TAG, "shouldInterceptRequest: ${request.url}")
                return super.shouldInterceptRequest(view, request)
            }

            /**
             * run on main thread
             */
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return false
                }
                Log.d(this.TAG, "shouldOverrideUrlLoading: $url")

                // can checkurl here
                return super.shouldOverrideUrlLoading(view, url)
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val url = request.url
                Log.d(this.TAG, "shouldOverrideUrlLoading: $url")
                return super.shouldOverrideUrlLoading(view, request)
            }

            /**
             *  Parameter specified as non-null is null
             *  kotlin will check parameter is null or not , we meed modify Bitmap to Bitmap?
             */
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d(this.TAG, "onPageStarted: $url")
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                Log.d(this.TAG, "onPageFinished: $url")
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                //  cause load resource like icon/font fail also will call this method ,
                //  use isForMainFrame to exclude those
                if (request.isForMainFrame) {
                    // load main page failed
                    // can show a default html page here
                }
                Log.e(this.TAG, "onReceivedError: ${error.description}")
            }

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                //  this method also will be called in Android N
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return
                }
                Log.e(this.TAG, "onReceivedError: $description")
            }

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                handler.proceed()
                Log.e(this.TAG, "onReceivedSslError: ${error}")
                super.onReceivedSslError(view, handler, error)
            }
        }
    }
}
