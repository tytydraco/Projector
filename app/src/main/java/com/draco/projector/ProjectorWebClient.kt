package com.draco.projector

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar

class ProjectorWebClient(
    private val activity: Activity,
    private val progress: ProgressBar
) : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        progress.visibility = View.VISIBLE
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progress.visibility = View.GONE
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        Snackbar.make(view!!.rootView, R.string.error_message, Snackbar.LENGTH_SHORT)
            .setAction(R.string.error_exit) { activity.finish() }
            .show()
    }
}