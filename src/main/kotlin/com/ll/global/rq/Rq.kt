package com.ll.global.rq

class Rq(cmd: String) {
    val command: String
    private val paramMap = mutableMapOf<String, String>()

    init {
        val cmdBits = cmd.split("?", limit = 2)
        command = cmdBits[0].trim()

        if (cmdBits.size == 2) {
            val queryStr = cmdBits[1]
            queryStr.split("&").forEach { param ->
                val token = param.split("=", limit = 2)
                if(token.size == 2) {
                    paramMap[token[0]] = token[1]
                }
            }
        }
    }

    fun getParam(name: String, default: String): String {
        return paramMap[name] ?: default
    }

    fun getParamAsInt(name: String, default: Int): Int {
        return try {
            paramMap[name]?.toInt() ?: default
        } catch (e: NumberFormatException) {
            default
        }
    }
}