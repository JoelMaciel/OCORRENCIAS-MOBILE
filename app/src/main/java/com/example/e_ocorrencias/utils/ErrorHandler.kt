package com.example.e_ocorrencias.utils

import okhttp3.ResponseBody
import org.json.JSONObject

class ErrorHandler {
    fun parse(errorBody: ResponseBody?, defaultMessage: String): String {
        return try {
            JSONObject(errorBody?.string() ?: "").getString("message")
        } catch (e: Exception) {
            "$defaultMessage: ${e.message}"
        }
    }
}