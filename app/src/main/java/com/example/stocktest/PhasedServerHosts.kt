package com.example.stocktest

internal class PhasedServerHosts(private val phase: String): ServerHosts() {
    override val url: String
        get() = when (phase) {
            Phase.BETA.phaseName -> "https://beta.kisvn.vn:8443/"
            else -> "https://trading.kisvn.vn/"
        }
}

fun ServerHosts.Companion.withPhase(phase: String): ServerHosts = PhasedServerHosts(phase)