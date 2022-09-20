package com.example.stocktest

internal class PhasedServerHosts(private val phase: Phase): ServerHosts() {
    override val url: String
        get() = when (phase) {
            Phase.BETA -> "https://beta.kisvn.vn:8443"
            Phase.PRODUCTION -> "https://trading.kisvn.vn"
        }
}

fun ServerHosts.Companion.withPhase(phase: Phase): ServerHosts = PhasedServerHosts(phase)