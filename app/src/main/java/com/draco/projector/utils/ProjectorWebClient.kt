package com.draco.projector.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.net.http.SslError
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import com.draco.projector.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

    @SuppressLint("WebViewClientOnReceivedSslError")
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        MaterialAlertDialogBuilder(activity)
            .setTitle(R.string.ssl_title)
            .setMessage(R.string.ssl_message)
            .setPositiveButton(R.string.ssl_accept) { _, _ -> handler?.proceed() }
            .setNegativeButton(R.string.ssl_decline) { _, _ ->
                handler?.cancel()
                activity.finish()
            }
            .show()
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