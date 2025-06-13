package com.example.proyecto

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @GET(Constants.Path_Turistas)
    suspend fun getTuristas():List<Turista>
    @GET(Constants.Path_Visitante)
    suspend fun getVisitante():List<Visitante>
    @GET(Constants.Path_Categoria)
    suspend fun getCategorias(): List<ItemCategoria>

    @GET(Constants.Path_Sitios)
    suspend fun getSitios():List<ItemSitio>
    @GET(Constants.Path_Ubicaciones)
    suspend fun getUbicaciones():List<Ubicaciones>
    @POST(Constants.Path_Turistas)
    suspend fun sentTuristas(@Body turista: Turista):Response<Turista>
    @POST(Constants.Path_Visitante)
    suspend fun sentVisitante(@Body visitante: Visitante):Response<Visitante>
}