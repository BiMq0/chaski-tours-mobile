package com.example.proyecto

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class ItemCategoria(
    val nombre_categoria: String,
    val id_sitio: String
)