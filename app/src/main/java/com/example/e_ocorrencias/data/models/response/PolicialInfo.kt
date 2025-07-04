package com.example.e_ocorrencias.data.models.response

data class PolicialInfo(
    val id: String,
    val nome: String,
    val matricula: String,
    val roles: List<String>
) {
    companion object {
        val EMPTY = PolicialInfo(
            id = "",
            nome = "",
            matricula = "",
            roles = emptyList()
        )
    }
}
