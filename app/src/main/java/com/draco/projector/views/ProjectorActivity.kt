package com.draco.projector.views

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.draco.projector.utils.ProjectorWebClient
import com.draco.projector.R
import com.draco.projector.viewmodels.LoginActivityViewModel

class ProjectorActivity : AppCompatActivity() {
    private val viewModel: LoginActivityViewModel by viewModels()

    private lateinit var projector: WebView
    private lateinit var progress: ProgressBar

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projector)

        projector = findViewById(R.id.projector)
        progress = findViewById(R.id.progress)

        val address = intent.extras!!.getString("address")!!
        val port = intent.extras!!.getString("port")!!
        val password = intent.extras!!.getString("password")!!
        val https = intent.extras!!.getBoolean("https")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH)
            window.decorView.setOnApplyWindowInsetsListener { view, windowInsets ->
                view.post { immersive() }
                return@setOnApplyWindowInsetsListener windowInsets
            }

        val protocol = viewModel.getHttpProtocol(https)
        val tokenSuffix = viewModel.getTokenSuffix(password)
        val projectorWebClient = ProjectorWebClient(this, progress)
        val url = viewModel.getUrl(protocol, address, port, tokenSuffix)

        projector.apply {
            webViewClient = projectorWebClient
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    }

    private fun immersive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            with (window.insetsController!!) {
                hide(
                    WindowInsets.Type.statusBars() or
                    WindowInsets.Type.navigationBars()
                )
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN
            )
        }
    }
}