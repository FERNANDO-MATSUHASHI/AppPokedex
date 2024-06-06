package com.example.apppokemon.api

import com.example.apppokemon.model.Pokemons
import com.example.apppokemon.model.Sprites
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServicosAPI {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Pokemons

    @GET("pokemon/{id}/")
    suspend fun getPokemonsImage(
        @Path("id") id: String?
    ): Sprites

}
