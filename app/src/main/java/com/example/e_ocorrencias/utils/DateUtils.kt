package com.example.e_ocorrencias.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {
    fun formatDate(dateString: String): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            parser.timeZone = TimeZone.getTimeZone("UTC")

            val date = parser.parse(dateString)

            val adjustedDate = Date(date.time - (3 * 60 * 60 * 1000))

            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            formatter.timeZone = TimeZone.getDefault()

            formatter.format(adjustedDate)
        } catch (e: Exception) {
            "Data inválida"
        }
    }
}