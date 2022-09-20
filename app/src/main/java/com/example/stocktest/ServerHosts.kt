package com.example.stocktest

open class ServerHosts {
    open val url = "https://beta.kisvn.vn:8443"

    companion object
}

//static var baseURL: String {
//    #if SANDBOX
//    return "https://beta.kisvn.vn:8443/rest/api/"
//    #else
//    return "https://trading.kisvn.vn/rest/api/"
//    #endif
//}