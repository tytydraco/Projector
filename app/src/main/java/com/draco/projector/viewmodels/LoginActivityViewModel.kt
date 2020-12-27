package com.draco.projector.viewmodels

import androidx.lifecycle.ViewModel
import com.draco.projector.models.UrlModel

class LoginActivityViewModel : ViewModel() {
    fun getHttpProtocol(secure: Boolean) = if (secure) "https" else "http"
    fun getTokenSuffix(password: String) = if (password.isNotBlank()) "?token=$password" else ""
    fun getUrl(
        protocol: String,
        address: String,
        port: String,
        tokenSuffix: String
    ) = UrlModel(protocol, address, port, tokenSuffix).toString()
}