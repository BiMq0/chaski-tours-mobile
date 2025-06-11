package com.example.proyecto

import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @GET(Constants.Path_Turistas)
    suspend fun getTuristas():List<Visitante>
    @GET(Constants.Path_Sitios)
    suspend fun getSitios():List<ItemSitio>
    @GET(Constants.Path_Ubicaciones)
    suspend fun getUbicaciones():List<Ubicaciones>
    @POST(Constants.Path_Sitios)
    suspend fun sentTuristas():ItemSitio
}