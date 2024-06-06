package com.example.apppokemon.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InstanciaRetrofit {

    private val ENDERECO_DA_API = "https://pokeapi.co/api/v2/"

    val api: ServicosAPI by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(ENDERECO_DA_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ServicosAPI::class.java)
    }

    // API para Peso e Altura
    private val ENDERECO_DA_API_PESO_ALURA = "https://pokeapi.co/api/v2/"

    val apiPesoAltura: ServicosAPI by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(ENDERECO_DA_API_PESO_ALURA)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ServicosAPI::class.java)
    }
}