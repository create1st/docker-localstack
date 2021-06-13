package com.craftandtechnology.dockerlocalhost.state

import java.util.*

class StateHolder {
    lateinit var transactionId: String
    var result: Any? = null

    fun init() {
        transactionId = UUID.randomUUID().toString()
        result = null
    }
}