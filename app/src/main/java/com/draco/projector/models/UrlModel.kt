package com.draco.projector.models

class UrlModel(
    private val protocol: String,
    private val address: String,
    private val port: String,
    private val tokenSuffix: String
) {
    override fun toString(): String = "$protocol://$address:$port$tokenSuffix"
}