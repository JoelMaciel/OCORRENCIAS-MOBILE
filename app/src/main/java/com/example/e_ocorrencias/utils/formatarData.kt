package com.example.e_ocorrencias.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatarData(dataString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val localDateTime = LocalDateTime.parse(dataString, formatter)
    return localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
}