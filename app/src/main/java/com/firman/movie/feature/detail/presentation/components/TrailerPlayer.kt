package com.firman.movie.feature.detail.presentation.components

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

// --- Constants for YouTube Player ---
private const val YOUTUBE_CLEANUP_CSS = """
    .mobile-topbar-header-content,
    .topbar-menu-button-avatar-button,
    ytm-pivot-bar-renderer,
    .player-controls-background,
    .ytm-autonav-bar,
    ytm-related-items-renderer,
    .ytm-app-header,
    .ytm-comment-section-renderer,
    .slim-video-metadata-header,
    .mobile-topbar-header,
    #header,
    ytm-item-section-renderer[section-identifier="comments-entry-point"],
    ytm-item-section-renderer[section-identifier="related-items"],
    .smart-button-renderer-group,
    .c3-material-button-layout-content,
    ytm-engagement-panel-section-list-renderer,
    .related-chips-slot-wrapper,
    ytm-chip-cloud-renderer,
    .single-column-browse-results-tabs {
        display: none !important;
    }
    body {
        background: #000 !important;
    }
"""

private const val YOUTUBE_CLEANUP_SCRIPT = """
    (function() {
        var style = document.createElement('style');
        style.textContent = `$YOUTUBE_CLEANUP_CSS`;
        document.head.appendChild(style);
    })();
"""

@Composable
fun TrailerPlayer(
    youtubeKey: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isFullScreen by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var customView by remember { mutableStateOf<View?>(null) }
    var customViewCallback by remember { mutableStateOf<WebChromeClient.CustomViewCallback?>(null) }

    BackHandler(enabled = isFullScreen) {
        customViewCallback?.onCustomViewHidden()
    }

    val watchUrl = remember(youtubeKey) { "https://m.youtube.com/watch?v=$youtubeKey" }

    val webView = remember {
        createYouTubeWebView(
            context = context,
            onLoadingChange = { isLoading = it },
            onFullScreenChange = { view, callback, isFull ->
                customView = view
                customViewCallback = callback
                isFullScreen = isFull
            }
        )
    }

    DisposableEffect(youtubeKey) {
        Log.d("TrailerPlayer", "Loading YouTube page: $watchUrl")
        webView.loadUrl(watchUrl)
        onDispose {
            Log.d("TrailerPlayer", "Disposing WebView")
            webView.loadUrl("about:blank")
            webView.destroy()
        }
    }

    Box(
        modifier = modifier.background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { webView }
        )

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
private fun createYouTubeWebView(
    context: android.content.Context,
    onLoadingChange: (Boolean) -> Unit,
    onFullScreenChange: (View?, WebChromeClient.CustomViewCallback?, Boolean) -> Unit
): WebView {
    var activeCustomView: View? = null
    var activeCustomViewCallback: WebChromeClient.CustomViewCallback? = null

    return WebView(context).apply {
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            cacheMode = WebSettings.LOAD_DEFAULT
            mediaPlaybackRequiresUserGesture = true
            userAgentString = "Mozilla/5.0 (Linux; Android 14; Pixel 8 Pro) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Mobile Safari/537.36"
        }

        webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                onLoadingChange(true)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.evaluateJavascript(YOUTUBE_CLEANUP_SCRIPT, null)
                onLoadingChange(false)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url?.toString() ?: return false
                return !url.contains("youtube.com") && !url.contains("youtu.be") &&
                        !url.contains("google.com") && !url.contains("accounts.google")
            }
        }

        webChromeClient = object : WebChromeClient() {
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                if (view == null) return
                Log.d("TrailerPlayer", "Entering fullscreen")
                activeCustomView = view
                activeCustomViewCallback = callback
                onFullScreenChange(view, callback, true)

                val activity = context as? Activity ?: return
                val decorView = activity.window.decorView as FrameLayout
                view.setBackgroundColor(android.graphics.Color.BLACK)
                decorView.addView(
                    view,
                    FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
            }

            override fun onHideCustomView() {
                Log.d("TrailerPlayer", "Exiting fullscreen")
                val activity = context as? Activity ?: return
                val decorView = activity.window.decorView as FrameLayout

                activeCustomView?.let { decorView.removeView(it) }
                activeCustomViewCallback?.onCustomViewHidden()
                onFullScreenChange(null, null, false)
                
                activeCustomView = null
                activeCustomViewCallback = null

                this@apply.onResume()
                this@apply.requestFocus()
            }
        }
        setBackgroundColor(android.graphics.Color.BLACK)
    }
}
